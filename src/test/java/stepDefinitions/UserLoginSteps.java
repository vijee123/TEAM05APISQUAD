package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requests.UserLoginRequests;
import utilities.CommonUtils;
import static org.testng.Assert.assertEquals;

public class UserLoginSteps {

    UserLoginRequests loginReq = new UserLoginRequests();
    RequestSpecification requestSpec;
    Response response;

    @Given("Admin sets No Auth")
    public void admin_sets_no_auth() {
        // Base initialization without a token
        requestSpec = loginReq.setNoAuth(); 
        requestSpec.baseUri(CommonUtils.baseURI);
    }
    
    
    @When("Admin calls login HTTPS method with endpoint")
    public void admin_calls_api() {
        response = loginReq.sendRequest(requestSpec);
        response.then().log().all();
    }
    
    @Then("Admin validates response")
    public void admin_validates_response() {
        int expectedCode = loginReq.getStatusCode();
        assertEquals(response.getStatusCode(), expectedCode, "Status Code Mismatch!");
        
        System.out.println(response);
        
        String expectedMsg = loginReq.getStatusText();
        loginReq.validateResponseMessage(expectedMsg, response.getStatusCode(), "scenario", response);
        loginReq.saveToken(response); // Automatically captures token if status is 200/201
    }
    
    
    @Given("Admin sets the auth {string}")
    public void admin_sets_the_auth(String authType) {
        requestSpec = loginReq.setNoAuth(); // Start clean
        
        if (authType.equalsIgnoreCase("Bearer Token")) {
            // Ensure this method retrieves the LATEST token saved by loginReq.saveToken()
        	String token = utilities.TokenManager.getToken(); 
            requestSpec.header("Authorization", "Bearer " + token);
        }
    }
    


    @Given("Admin sets authorization {string} and creates logout request")
    public void admin_sets_authorization_and_logout(String authType) {
        loginReq.createGenericRequest("Admin logout", "Login");
        requestSpec = loginReq.setNoAuth(); 
        requestSpec = loginReq.applyAuthentication(authType, requestSpec);
        requestSpec.body(""); // Logout usually has no body
    }

    @Given("Admin creates request with {string}")
    public void admin_creates_login_request(String scenario) throws Exception {
        loginReq.createLoginRequest(scenario);
        // Handle logic for 'invalid base URL'
        if (scenario.equalsIgnoreCase("invalid base URL")) {
            requestSpec.baseUri("https://invalid-lms-backend.herokuapp.com");
        }
        requestSpec = loginReq.buildRequest(requestSpec);
    }

    @Given("Admin creates forgot password request with {string}")
    public void admin_creates_forgot_pwd(String scenario) {
        loginReq.createGenericRequest(scenario, "Login");
        requestSpec = loginReq.buildForgotPasswordBody(requestSpec);
    }

    @And("Admin creates reset password request with {string}")
    public void admin_creates_reset_pwd(String scenario) {
        loginReq.createGenericRequest(scenario, "Login");
        requestSpec = loginReq.buildResetPasswordBody(requestSpec);
    }
  
}