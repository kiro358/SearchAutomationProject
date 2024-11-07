# **eBay Search Functionality Testing**

### **Project Description**
This project involves testing the **search functionality of eBay**, ensuring that it works seamlessly across different scenarios, including valid and invalid inputs, security vulnerabilities, and advanced search features. The focus is on validating the user experience, security, and responsiveness of the search feature, both on desktop and mobile platforms. The test cases and scenarios are designed to handle edge cases, user input variability, and different contexts, such as filtering, sorting, and accessibility.

### **Frameworks and Tools**
The project leverages the following frameworks and tools:

1. **Selenium WebDriver**:
   - Automates browser actions for testing user interactions with the search bar and filters.
   - Handles dynamic elements like dropdowns and pop-ups.

2. **Cucumber**:
   - Implements Behavior-Driven Development (BDD) using Gherkin syntax for test scenarios.
   - Ensures that scenarios are clear and readable for both technical and non-technical stakeholders.

3. **JUnit**:
   - Provides the test runner for executing test cases.
   - Integrates with Cucumber to validate scenarios and handle assertions.

4. **Java**:
   - Programming language used for creating the test automation scripts.
   - Handles logic for interacting with web elements and assertions.

5. **WebDriverManager**:
   - Simplifies the management of browser drivers (e.g., ChromeDriver, GeckoDriver).

6. **TestNG (optional)**:
   - For additional parallel execution or advanced reporting (if used).

7. **Maven**:
   - Manages dependencies like Selenium, Cucumber, and WebDriverManager.
   - Handles project builds and integration with CI/CD tools.

8. **Extent Reports/Allure (optional)**:
   - For generating detailed test execution reports.

### **Key Features Tested**
- **Search Behavior**:
  - Valid and invalid inputs.
  - Case sensitivity, special characters, and partial matches.
- **Security**:
  - SQL Injection and Cross-Site Scripting (XSS) protection.
- **Advanced Features**:
  - Sorting, filtering, and advanced search criteria.
- **User Experience**:
  - Pagination, suggestions, and recent searches.
- **Responsiveness**:
  - Mobile and desktop compatibility.
- **Accessibility**:
  - Keyboard navigation and multi-language support.

This project ensures a robust, user-friendly, and secure search functionality for eBay's platform.


**Test Cases with eBay Search Functionality:**

1. **Test Case: Search with a valid product name.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Enter "Nike Air Max" in the search bar.
     3. Click the search button.
   - **Expected Result:** Search results display listings related to "Nike Air Max."

2. **Test Case: Search with a partial product name.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Enter "Nike Air" in the search bar.
     3. Click the search button.
   - **Expected Result:** Search results display listings related to "Nike Air Max" and other related products.

3. **Test Case: Search with an invalid/non-existing product.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Enter "XYZ12345" in the search bar.
     3. Click the search button.
   - **Expected Result:** Display a message indicating no results were found.

4. **Test Case: Search with empty input.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Leave the search bar empty.
     3. Click the search button.
   - **Expected Result:** User is redirected to the categories page.

5. **Test Case: Search with special characters.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Enter "!@#$%^&*" in the search bar.
     3. Click the search button.
   - **Expected Result:** Input is handled gracefully without errors.

6. **Test Case: SQL Injection attempt.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Enter "'; DROP TABLE products;--" in the search bar.
     3. Click the search button.
   - **Expected Result:** Input is sanitized; no security breach occurs.

7. **Test Case: Cross-Site Scripting (XSS) attack attempt.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Enter "\<script>alert('XSS');\</script>" in the search bar.
     3. Click the search button.
   - **Expected Result:** Input is sanitized; script is not executed.

8. **Test Case: Search suggestions appear as typing.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Start typing "Lapto" in the search bar.
   - **Expected Result:** Autocomplete suggestions like "Laptop" appear.

9. **Test Case: Search results pagination.**
   - **Steps:**
     1. Navigate to the eBay homepage.
     2. Search for "Shoes."
     3. Navigate to the second page of results.
   - **Expected Result:** Second page displays additional relevant listings.

10. **Test Case: Filtering search results.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Search for "Camera."
      3. Apply filter Brand: "Canon."
    - **Expected Result:** Display only "Canon" camera listings.

11. **Test Case: Sorting search results.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Search for "Headphones."
      3. Sort results by "Price + Shipping: lowest first."
    - **Expected Result:** Products are sorted accordingly.

12. **Test Case: Case sensitivity check.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Enter "APPLE Watch" in the search bar.
      3. Click the search button.
    - **Expected Result:** Search is case-insensitive; relevant products are displayed.

13. **Test Case: Handling misspelled words.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Enter "iphnoe" in the search bar.
      3. Click the search button.
    - **Expected Result:** Suggest "Did you mean 'iPhone'?" or display results for "iPhone."

14. **Test Case: Search using synonyms.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Enter "Mobile Phone" in the search bar.
      3. Click the search button.
    - **Expected Result:** Display products listed under "Cell Phones & Smartphones."

15. **Test Case: Advanced search options.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Enter "Golf Set" in the search bar.
      3. Click the search button.
      4. Use advanced search.
      5. Set criteria: "Price under $500."
    - **Expected Result:** Display products matching the criteria.

16. **Test Case: Search within a category.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Navigate to the "Electronics" category.
      3. Enter "Tablet" in the search bar.
      4. Click the search button.
    - **Expected Result:** Display tablets within the "Electronics" category.

17. **Test Case: Recent searches are displayed.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Perform several searches.
      3. Click on the search bar.
    - **Expected Result:** Display a list of recent search terms.

18. **Test Case: Multi-language search support.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Enter "鞋子" (Chinese for "Shoes") in the search bar.
      3. Click the search button.
    - **Expected Result:** Display products relevant to the term.

19. **Test Case: Accessibility via keyboard.**
    - **Steps:**
      1. Navigate to the eBay homepage.
      2. Use Tab to focus on the search bar.
      3. Enter "Book."
      4. Press Enter.
    - **Expected Result:** Search executes without mouse interaction.

20. **Test Case: Mobile responsiveness.**
    - **Steps:**
      1. Access the eBay site on a mobile device.
      2. Enter "Nike Air Max" in the search bar.
      3. Click the search button.
    - **Expected Result:** Search functionality works seamlessly on mobile; results related to "Nike Air Max" are displayed.

