Feature: Search Functionality Testing

  Scenario Outline: Search with various inputs
    Given I am on the homepage
    When I enter "<search_term>" in the search bar
    And I click the search button
    Then I should see "<expected_result>"

    Examples:
      | search_term                  | expected_result                                  |
      | Nike Air Max                 | Products related to "Nike Air Max" are displayed |
      | XYZ1234                      | "No results found" message is displayed          |
      | ''                           | Prompt to enter a search term is displayed       |
      | '!@#$%^&*'                   | Graceful handling without errors                 |
      | '<script>alert("XSS")</script>' | Input is sanitized, no script execution       |
