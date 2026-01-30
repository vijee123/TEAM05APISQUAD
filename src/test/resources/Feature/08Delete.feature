@program
Feature: Validating Program Module API
Background:
Given Admin set Authorization to Bearer token
 @deleteProgramByName
 Scenario Outline: Check if admin is able to Delete program by programName with valid/invalid details
 Given Admin creates delete Request for the LMS with request body "<Scenario>"
 When Admin sends HTTPS Request and request Body with "programName" endpoint
 Then Admin receives StatusCode with statusText "<Scenario>" for Program
 Examples:
 | Scenario                           |
 | DeleteProgramByInvalidName         |
 | DeleteProgramByNameInvalidEndpoint |
 | DeleteProgramByNameInvalidMethod   |
 | DeleteProgramByNameInvalidBaseURI  |
 | DeleteProgramByNameNoAuth          |
 | DeleteProgramByValidName           |
 
 
 
 @deleteProgramByProgramId
Scenario Outline: Check if Admin able to delete a program with valid/invalid program ID
Given Admin creates DELETE Request for the LMS API endpoint with valid_invalid program ID "<Scenario>"
When Admin sends HTTPS Request with endpoint for delete program
Then Admin receives StatusCode for program delete with statusText
Examples:
| Scenario                         |
| valid LMS API,invalid program ID |
| DeleteProgramByProgramIdNoAuth   |
| valid program ID                 |

@DELETEuser
Scenario Outline: Check if Admin is able to delete Valid/Invalid UserID
    Given Admin creates DELETE Request with valid or invalid UserID for "<Scenario>"
    When Admin sends HTTPS user Request with endpoint
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |Delete with Valid UserID|
      |Delete with Invalid UserID|

