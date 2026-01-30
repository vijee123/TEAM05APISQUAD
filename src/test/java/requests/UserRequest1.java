package requests;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Commons;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.UserPayload1;
import pojo.UserPojo1;
import utilities.CommonUtils;
import utilities.TokenManager;
import utilities.TokenManager.TokenManager1;

public class UserRequest1 extends CommonUtils {
	 private Map<String, String> currentRow;
	    private Response response;
	    private UserPojo1 userPojo;
	    private static final String INVALID_TOKEN = "jbnsjokfi";

	    public RequestSpecification setAuth() {
	        RestAssured.baseURI = CommonUtils.endpoints.getString("baseUrl");
	        TokenManager.setToken("");
	        return given().header("Authorization", "Bearer " + TokenManager1.getToken());
	    }

	    public void createUser(String scenario) throws IOException, InvalidFormatException, ParseException {
	        Map<String, Object> userDetails = new UserPayload1().getDataFromExcel(scenario);
	        if (userDetails != null) {
	            this.userPojo = (UserPojo1) userDetails.get("userPojo");
	            this.currentRow = (Map<String, String>) userDetails.get("currentRow");
	        }
	    }

	    public RequestSpecification buildRequest(RequestSpecification requestSpec) {
	        if (requestSpec == null) throw new IllegalStateException("RequestSpecification is not initialized.");
	        if (currentRow == null) throw new IllegalStateException("currentRow is null. Call createUser() first.");

	        String scenarioName = currentRow.get("ScenarioName");

	        // AUTH variations
	        if (scenarioName.contains("NoAuth")) {
	            requestSpec = given();
	        } else if (scenarioName.contains("InvalidToken")) {
	            requestSpec = given().header("Authorization", "Bearer " + INVALID_TOKEN);
	        } else if (scenarioName.contains("InvalidBaseURI")) {
	            RestAssured.baseURI = CommonUtils.endpoints.getString("invalidBaseUrl");
	            requestSpec = given().header("Authorization", "Bearer " + TokenManager.getToken1());
	        }
	        String operationType = currentRow.get("OperationType");
	        if (!"GET".equalsIgnoreCase(operationType) && !"DELETE".equalsIgnoreCase(operationType)) {
	            String contentType = currentRow.get("ContentType");
	            if (contentType != null && !contentType.trim().isEmpty()) {
	                requestSpec.contentType(contentType);
	            }
	        }

	        // 3. BODY ONLY for POST/PUT/PATCH - NEVER GET/DELETE
	        if (("POST".equalsIgnoreCase(operationType) || "PUT".equalsIgnoreCase(operationType) || "PATCH".equalsIgnoreCase(operationType)) 
	            && userPojo != null && !scenarioName.contains("WithoutRequestBody")) {
	            requestSpec.body(userPojo);
	            logRequestBody();
	        }

	     // Path parameters
	        String roleId = currentRow.get("roleId");
	        if (roleId != null && !roleId.trim().isEmpty()) {
	            requestSpec.pathParam("roleId", roleId);
	        }
	        String programId = currentRow.get("programId");
	        if (programId != null && !programId.trim().isEmpty()) {
	            requestSpec.pathParam("programId", programId);
	        }
	        String paramValue = currentRow.get("userId");
	        if (paramValue != null && !paramValue.trim().isEmpty()) {
	            
	            String[] possibleParamNames = {"userId", "id", "UserId", "userID"};
	            for (String paramName : possibleParamNames) {
	                if (paramName != null) {
	                    requestSpec.pathParam(paramName, paramValue);
	                    break;
	                }}}

	       return requestSpec;        
	        }
	    private void logRequestBody() {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            String jsonBody = mapper.writeValueAsString(userPojo);
	        } catch (Exception e) {
	           
	        }
	    }

	    public Response sendRequest(RequestSpecification requestSpec) {
	        String endpoint = currentRow.get("EndPoint");
	        response = CommonUtils.getResponse(requestSpec, endpoint);
	        return response;
	    }

	    public int getStatusCode() {
	        return (int) Double.parseDouble(currentRow.get("StatusCode"));
	    }

	    public String getStatusText() {
	        String scenarioName = currentRow.get("ScenarioName");
	        if (scenarioName.equalsIgnoreCase("Invalid Endpoint") || 
	            scenarioName.equalsIgnoreCase("Mandatory") || 
	            scenarioName.equalsIgnoreCase("Full Details")) {
	            return null;
	        }
	        return currentRow.get("StatusText");
	    }

	    public void saveResponseBody(Response response) {
	        String userId = response.jsonPath().getString("userId");
	        Commons.setuserId(userId);
	    }

	    public Map<String, Object> getUsersByRoleIdData(String scenario, UserPojo1 userPojo) throws Exception {
	        UserPayload1 userPayload = new UserPayload1();
	        Map<String, Object> testData = userPayload.getUsersByRoleData(scenario, userPojo);
	        String roleId = (String) testData.get("roleId");
	        
	        if (roleId == null || roleId.trim().isEmpty()) {
	            throw new IllegalArgumentException("roleId cannot be null or empty for scenario: " + scenario);
	        }
	        
	        testData.put("endpoint", "/users/role/" + roleId);
	        return testData;
	    }

	    public Map<String, Object> getUserDetailsData(String scenario, UserPojo1 userPojo) throws Exception {
	        UserPayload1 userPayload = new UserPayload1();
	        Map<String, Object> testData = userPayload.getUserDetails(scenario, userPojo);
	        String userId = (String) testData.get("userId");
	          
	        if (userId == null || userId.trim().isEmpty()) {
	            throw new IllegalArgumentException("userId cannot be null or empty for scenario: " + scenario);
	        }
	        
	        String endpoint = userId.contains("id") ? "/users/details/" + userId : "/users/" + userId;
	        testData.put("endpoint", endpoint);
	        testData.put("userId", userId);
	        return testData;
	    }
	    
	    public Map<String, Object> getDeleteUserData(String scenario, UserPojo1 userPojo) throws Exception {
	        UserPayload1 userPayload = new UserPayload1();
	        Map<String, Object> testData = userPayload.getDeleteUserData(scenario);  // From Excel
	        String userId = (String) testData.get("userId");
	        
	        if (userId == null || userId.trim().isEmpty()) {
	            throw new IllegalArgumentException("userId cannot be null or empty for scenario: " + scenario);
	        }
	        
	        String endpoint = "/users/" + userId;
	        testData.put("endpoint", endpoint);
	        testData.put("userId", userId);
	        return testData;
	    }
	    
	    public Map<String, Object> getUpdateUserData(String scenario, UserPojo1 userPojo) throws Exception {
	        UserPayload1 userPayload = new UserPayload1();
	        Map<String, Object> testData = userPayload.getUpdateUserData(scenario);  // Generic method in payload
	        String userId = (String) testData.get("userId");
	        
	        if (userId == null || userId.trim().isEmpty()) {
	            throw new IllegalArgumentException("userId cannot be null or empty for scenario: " + scenario);
	        }

	        String operationType = (String) testData.get("operationType");
	        String endpoint;
	        
	        if ("UpdateLogin".equalsIgnoreCase(operationType)) {
	            endpoint = "/users/userLogin/" + userId;
	        } else if ("UpdateRole".equalsIgnoreCase(operationType)) {
	            endpoint = "/users/roleId/" + userId;
	        } else if ("UpdateUser".equalsIgnoreCase(operationType)) {
	            endpoint = "/users/" + userId;
	        }
	        else {
	            throw new IllegalArgumentException("Invalid operationType: " + operationType + " for scenario: " + scenario);
	        }
	        
	        testData.put("endpoint", endpoint);
	        testData.put("userId", userId);
	        testData.put("operationType", operationType);
	        return testData;
	    }
	    
	    public Map<String, Object> getUserDetailsByIdData(String scenario, UserPojo1 userPojo) throws Exception {
	        UserPayload1 userPayload = new UserPayload1();
	        Map<String, Object> testData = userPayload.getUserDetailsByIdData(scenario, userPojo);
	        String UserId = (String) testData.get("UserId");
	        
	        if (UserId == null || UserId.trim().isEmpty()) {
	            // load Excel row directly if payload method didn't find data
	            userPayload.currentRow = CommonUtils.getCurrentRow(scenario, "User");
	            if (userPayload.currentRow != null) {
	                UserId = userPayload.getStringValue(userPayload.currentRow.get("UserId"));
	            }
	            
	            if (UserId == null || UserId.trim().isEmpty()) {
	              
	                testData.put("UserId", "");
	                testData.put("skipTest", true); // Let step definition handle skip
	                return testData;
	            }
	        }
	        
	        // endpoint type from Excel or default
	        String endpointType = (String) testData.get("endpointType");
	        if (endpointType == null || endpointType.trim().isEmpty()) {
	            endpointType = "batchId";  // Default for this specific GET batch scenario
	        }
	        
	        String endpoint;
	        if ("batchId".equalsIgnoreCase(endpointType)) {
	            endpoint = "/users/user/" + UserId;
	        } else {
	            endpoint = "/users/details/" + UserId;
	        }
	        
	        testData.put("endpoint", endpoint);
	        testData.put("UserId", UserId);
	        testData.put("endpointType", endpointType);
	        return testData;
	    }
	    public Map<String, Object> getUsersbyProgramId(String scenario, UserPojo1 userPojo) throws Exception {
	        UserPayload1 userPayload = new UserPayload1();
	        Map<String, Object> testData = userPayload.getUsersByProgramData(scenario, userPojo);
	        String programId = (String) testData.get("programId");
	        
	        if (programId == null || programId.trim().isEmpty()) {
	            throw new IllegalArgumentException("programId cannot be null or empty for scenario: " + scenario);
	        }
	        
	        testData.put("endpoint", "/users/programs/" + programId);
	        return testData;
	    }

	}
