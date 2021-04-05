Feature: Article list page scenarios

  @regression
  Scenario: Open Article list scenario

    Given user opens viesure application
    When the application loads a list of articles
    Then user can see the articles

  Scenario: Scroll to bottom then to top
    Given user opens viesure application
    When user scrolls to the bottom
    Then user can see the articles
    When user scrolls to the top
    Then user can see the articles