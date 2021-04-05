Feature: Article list page scenarios

  Background:
    Given user opens viesure application

  @regression
  Scenario: Open Article list scenario
    Then user can see the articles list

  @regression
  Scenario: Scroll to bottom then to top
    When user scrolls to the bottom
    Then user can see the articles list
    When user scrolls to the top
    Then user can see the articles list

  @regression
  Scenario: Possible to open an article and navigate back with top button
  When user clicks on a visible article
  Then article detail page opens
  When user clicks on the detail page back button
  Then user can see the articles list

  @regression
  Scenario: Possible to open an article and navigate back with hardware button
    When user clicks on a visible article
    Then article detail page opens
    When user clicks on the hardware back button
    Then user can see the articles list

  @regression @showcase
  Scenario Outline: Possible to open visible article by title
    When user clicks on article with "<title>"
    Then article detail page opens
    And title of the page is the same as the clicked "<title>"
    When user clicks on the detail page back button
    Then user can see the articles list

    Examples:
      | title                       |
      | Sharable maximized analyzer |
      | Focused mobile alliance     |
      | Realigned multimedia framework|

#  Scenario: All list items are clickable
#    Then user can click on every list item