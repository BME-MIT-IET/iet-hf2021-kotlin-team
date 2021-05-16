Feature: bdd Test Functions
  Cucumber BDD testing based on the Espresso tests in the CalorieList.kt class

  @setup-feature
  Scenario Outline: User checks diagram visibility
    When adding <int> item to the list
    And tapping on first item
    And navigate to diagram
    Then diagram is visible

    Examples:
      | int |
      | 1   |

  @setup-feature
  Scenario Outline: User add two items then delete one
    Given adding <int> item to the list with number
    When delete second item
    Then one item in list

    Examples:
      | int |
      | 2   |

  @setup-feature
  Scenario Outline: User add one item then edit it
    When adding <int> item to the list
    When edit first item name to <string>
    Then check name changed to <string>

    Examples:
      | int |  | string  |
      | 1   |  | Testing |

  @setup-feature
  Scenario Outline: User add one lunch item then check page number
    Given adding <int> <string> item to the list
    When tapping on first item
    And navigate to diagram
    Then item has web page

    Examples:
      | int |  | string |
      | 1   |  | Lunch  |

  @setup-feature
  Scenario Outline: User add two item then order it by name
    Given adding <int> item to the list with number
    When order items by name
    Then order is correct after ordering by name

    Examples:
      | int |
      | 5   |