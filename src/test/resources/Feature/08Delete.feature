

@Logout
Scenario Outline: Admin logout
Given Admin sets authorization "<authType>" and creates logout request
When Admin calls login HTTPS method with endpoint
Then Admin validates response

Examples:
| authType     |
| Bearer Token |
| No Auth      |
| Old Token    |

