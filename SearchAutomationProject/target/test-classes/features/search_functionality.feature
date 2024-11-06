Feature: Search functionality on eBay

  Scenario Outline: Valid search term on eBay
    Given I am on the eBay homepage
    When I enter "<searchTerm>" in the search bar
    And I click the search button
    Then I should see results related to "<searchTerm>"

  Examples:
    | searchTerm   |
    | shoes        |
    | laptops      |
    | Nike shoes   |

  Scenario Outline: Invalid search term on eBay
    Given I am on the eBay homepage
    When I enter "<searchTerm>" in the search bar
    And I click the search button
    Then I should see a message indicating no results were found

  Examples:
    | searchTerm    |
    | !@#$%^&*      |
    | '; DROP TABLE products;-- |
    | <script>alert('XSS');</script> |