Feature: Calorie list functions
  Cucumber BDD testing based on the Espresso tests in the CalorieList.kt class

  @setup-feature
  Scenario Outline: User adds an item
    When adding <int> item to the list
    Then the list has <int> item
    And  the displayed data is correct

    Examples:
      | int |
      | 1   |


  @setup-feature
  Scenario Outline: User clears the list
    When adding <int> item to the list
    And the list is cleared
    Then  the list has 0 item

    Examples:
      | int |
      | 2   |

  @setup-feature
  Scenario Outline: User navigates to description screen
    Given one item with a description: <string>
    When item is tapped
    Then the description shown on the screen is <string>

    Examples:
      | string |
      | Testing   |

