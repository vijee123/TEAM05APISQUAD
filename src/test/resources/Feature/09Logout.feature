@LogoutAPI
Feature: Validating User logout Module API

  # REMOVE THE BACKGROUND ENTIRELY OR LEAVE IT EMPTY
  # Because your Scenario Outline handles authType dynamically

  @Logout
  Scenario Outline: Admin logout
    Given Admin sets authorization "<authType>" and creates logout request for userLoginModule
    When Admin calls login HTTPS method with endpoint
    Then Admin validates response

    Examples:
      | authType     |
      | Bearer Token |
      | No Auth      |
      | Old Token    |