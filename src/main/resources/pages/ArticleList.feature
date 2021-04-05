Feature: Article list page scenarios

  @regression
  Scenario: Open Article list scenario

    Given user opens viesure application
    When the application loads a list of articles
    Then user can verify the visible articles
#    |keyword|exclude|min|max |
#    |       |       |   |    |