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
    When tapping on first item
    Then the description shown on the screen is <string>

    Examples:
      | string |
      | Testing   |

  @setup-feature
  Scenario Outline: if item type is Workout, then there are two pages
    Given <int> item with <string> type
    When tapping on first item
    And swiping left <int2> time
    Then user is at the second page

    Examples:
      |int |int2  | string |
      |1   | 2    | Workout |

  @setup-feature
  Scenario Outline: User sorts by date and time
    Given an item with date: <date1> and time: <time1>
    And an item with date: <date2> and time: <time2>
    When ordering by date and time
    Then 0 th element has date: <date2> and time: <time2>
    And 1 th element has date: <date1> and time: <time1>

    Examples:
      |date1        |date2          | time1 |time2  |
      |2030.10.10   | 2022.11.14    | 10:20 | 10:20 |

  @setup-feature
  Scenario: User pressed delete all and dialog is shown
    When delete all is selected
    Then are you sure dialog is shown



