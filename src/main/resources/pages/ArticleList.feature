Feature: Article list page scenarios

  Scenario: Open Article list scenario

    Given user opens viesure application
    When the application loads a list of articles
    Then user can verify the visible articles