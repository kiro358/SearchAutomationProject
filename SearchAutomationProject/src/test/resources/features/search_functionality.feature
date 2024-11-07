Feature: Search functionality on eBay

  Scenario: Search with a valid product name
    Given I am on the eBay homepage
    When I enter "Nike Air Max" in the search bar
    And I click the search button
    Then I should see results related to "Nike Air Max"

  Scenario: Search with a partial product name
    Given I am on the eBay homepage
    When I enter "Nike Air" in the search bar
    And I click the search button
    Then I should see results related to "Nike Air Max"

  Scenario: Search with an invalid/non-existing product
    Given I am on the eBay homepage
    When I enter "XYZ12345" in the search bar
    And I click the search button
    Then I should see a message indicating no results were found

  Scenario: Search with empty input
    Given I am on the eBay homepage
    When I leave the search bar empty
    And I click the search button
    Then I should see the categories page

  Scenario: Search with special characters
    Given I am on the eBay homepage
    When I enter "!@#$%^&*" in the search bar
    And I click the search button
    Then the input is handled gracefully without errors

  Scenario: SQL Injection attempt
    Given I am on the eBay homepage
    When I enter "'; DROP TABLE products;--" in the search bar
    And I click the search button
    Then the input is sanitized and no security breach occurs

  Scenario: Cross-Site Scripting (XSS) attack attempt
    Given I am on the eBay homepage
    When I enter "<script>alert('XSS');</script>" in the search bar
    And I click the search button
    Then the input is sanitized and the script is not executed

  Scenario: Search suggestions appear as typing
    Given I am on the eBay homepage
    When I start typing "Lapto" in the search bar
    Then autocomplete suggestions like "Laptop" appear

  Scenario: Search results pagination
    Given I am on the eBay homepage
    When I search for "Shoes"
    And I navigate to the second page of results
    Then I should see additional relevant products

  Scenario: Filtering search results
    Given I am on the eBay homepage
    When I search for "Camera"
    And I apply filter Brand "Canon"
    Then I should see only "Canon" cameras

  Scenario: Sorting search results
    Given I am on the eBay homepage
    When I search for "Headphones"
    And I sort results by "Price + Shipping: lowest first"
    Then products are sorted accordingly

  Scenario: Case sensitivity check
    Given I am on the eBay homepage
    When I enter "APPLE Watch" in the search bar
    And I click the search button
    Then the search is case-insensitive and relevant products are displayed

  Scenario: Handling misspelled words
    Given I am on the eBay homepage
    When I enter "iphnoe" in the search bar
    And I click the search button
    Then I should be suggested results for "iphone"

  Scenario: Search using synonyms
    Given I am on the eBay homepage
    When I enter "Mobile Phone" in the search bar
    And I click the search button
    Then products listed under "Cell Phones & Smartphones" are displayed

  Scenario: Advanced search options
    Given I am on the eBay homepage
    When I enter "Golf Set" in the search bar
    And I click the search button
    When I use advanced search
    And I set criteria: "Price under $500"
    Then I should see products matching the criteria

  Scenario: Search within a category
    Given I am on the eBay homepage
    When I navigate to "Electronics" category
    And I search for "Tablet"
    Then products listed under "Tablets & eBook Readers" are displayed

  Scenario: Recent searches are displayed
    Given I have performed several searches
    When I click on the search bar
    Then I should see a list of recent search terms

  Scenario: Multi-language search support
    Given I am on the eBay homepage
    When I enter "鞋子" in the search bar
    And I click the search button
    Then I should see products relevant to the term

  Scenario: Accessibility via keyboard
    Given I am on the eBay homepage
    When I use Tab to focus on the search bar
    And I enter "Book"
    And I press Enter
    Then the search executes without mouse interaction

  @Mobile
  Scenario: Mobile responsiveness
    Given I access the site on a mobile device
    When I enter "Nike Air Max" in the search bar
    And I click the search button
    Then I should see results related to "Nike Air Max"
