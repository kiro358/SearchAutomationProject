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
