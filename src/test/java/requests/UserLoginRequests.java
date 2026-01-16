package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.UserLoginPayload;
import pojo.UserLoginPojo;
import utilities.CommonUtils;
import utilities.TokenManager;
import java.util.HashMap;
import java.util.Map;

public class UserLoginRequests extends CommonUtils {

    private UserLoginPojo userLoginPojo;

    /**
     * Initializes a basic request with standard headers and logging.
     */
    public RequestSpecification setNoAuth() {
        return RestAssured.given()
                .log().all() 
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    /**
     * Handles dynamic authentication based on the AuthType column in the Feature file.
     */
    public RequestSpecification applyAuthentication(String authType, RequestSpecification reqSpec) {
        if (authType.equalsIgnoreCase("Bearer Token")) {
            // Pulls the token stored during the login process
            return reqSpec.header("Authorization", "Bearer " + TokenManager.getToken());
        } else if (authType.equalsIgnoreCase("Old Token") || authType.equalsIgnoreCase("Invalid Token")) {
            // Hardcoded expired/invalid token for negative testing scenarios
            return reqSpec.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.expired_token_data");
        }
        // "No Auth" returns the spec as is
        return reqSpec;
    }

    /**
     * Loads Login specific data using the Payload builder and Excel.
     */
    public void createLoginRequest(String scenario) throws Exception {
        userLoginPojo = UserLoginPayload.getLoginPayload(scenario);
        currentRow = CommonUtils.getCurrentRow(scenario, "Login");
    }

    /**
     * Generic data loader for Forgot Password, Reset Password, and Logout.
     */
    public void createGenericRequest(String scenario, String sheetName) {
        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
    }

    /**
     * Builds the body for a standard Login request.
     */
    public RequestSpecification buildRequest(RequestSpecification reqSpec) {
        if (userLoginPojo != null) {
            reqSpec.body(userLoginPojo);
        }
        return reqSpec;
    }

    /**
     * Maps Excel 'email' column to the 'userLoginEmailId' key required by the API.
     */
    public RequestSpecification buildForgotPasswordBody(RequestSpecification reqSpec) {
        Map<String, String> body = new HashMap<>();
        String EmailId = (currentRow.get("EmailId") != null) ? currentRow.get("EmailId").trim() : "";
        body.put("userLoginEmailId", EmailId);
        return reqSpec.body(body);
    }

    /**
     * Reuses UserLoginPojo to build a Reset Password body (email + new password).
     */
    public RequestSpecification buildResetPasswordBody(RequestSpecification reqSpec) {
        String EmailId = currentRow.get("EmailId").trim();
        String Password = currentRow.get("Password").trim();
        
        UserLoginPojo resetData = new UserLoginPojo(EmailId, Password);
        return reqSpec.body(resetData);
    }

    /**
     * Prepares Logout request by applying auth and setting an empty body.
     */
    public RequestSpecification buildLogoutRequest(RequestSpecification reqSpec, String authType) {
        reqSpec = applyAuthentication(authType, reqSpec);
        return reqSpec.body(""); 
    }

    /**
     * Sends the request to the endpoint specified in the Excel sheet.
     */
    public Response sendRequest(RequestSpecification reqSpec) {
        String endpoint = currentRow.get("EndPoint");
        
        // Ensure the Base URI is correctly set from Global Config
        reqSpec.baseUri(CommonUtils.baseURI); 
        
        return CommonUtils.getResponse(reqSpec, endpoint);
    }

    /**
     * Extracts and stores the Token and UserID if the login is successful.
     */
    public void saveToken(Response response) {
        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            // Extracting values using jsonPath
            String token = response.jsonPath().getString("token");
            String uId = response.jsonPath().getString("userId");
            
            // Storing values in the TokenManager for use in subsequent steps
            if (token != null && !token.isEmpty()) {
                TokenManager.setToken(token);
                System.out.println("Token saved successfully.");
            }
            
            if (uId != null && !uId.isEmpty()) {
                TokenManager.setUserId(uId);
                System.out.println("User ID saved successfully: ${uId}");
            }
        } else {
            System.out.println("Login failed with status Token and UserID not saved.");
        }
    }

    /**
     * Parses the Excel Status Code (handles cases like 200.0).
     */
    public int getStatusCode() {
        String statusCodeStr = currentRow.get("StatusCode");
        return (int) Double.parseDouble(statusCodeStr);
    }

    public String getStatusText() {
        return currentRow.get("StatusText");
    }
    

}