package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.HomePage;
import pages.SearchResultsPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class SearchSteps {

    WebDriver driver = Hooks.driver;
    HomePage homePage = new HomePage(driver);
    SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("I am on the eBay homepage")
    public void i_am_on_the_ebay_homepage() {
        driver.get("https://www.ebay.com");
    }

    @Given("I access the site on a mobile device")
    public void i_access_the_site_on_a_mobile_device() {
        driver.get("https://www.ebay.com");
    }
    
    @Given("I have performed several searches")
    public void i_have_performed_several_searches() {
        driver.get("https://www.ebay.com");
        homePage.enterSearchTerm("Baby Seat");
        homePage.clickSearchButton();
        homePage.enterSearchTerm("Thermoflask");
        homePage.clickSearchButton();
        homePage.enterSearchTerm("Airpods");
        homePage.clickSearchButton();
    }

    @When("I enter {string} in the search bar")
    public void i_enter_in_the_search_bar(String searchTerm) {
        homePage.enterSearchTerm(searchTerm);
    }

    @When("I leave the search bar empty")
    public void i_leave_the_search_bar_empty() {
        homePage.enterSearchTerm("");
    }

    @When("I start typing {string} in the search bar")
    public void i_start_typing_in_the_search_bar(String partialTerm) {
        homePage.enterPartialSearchTerm(partialTerm);
    }

    @When("I use advanced search")
    public void i_use_advanced_search() {
        homePage.clickAdvancedSearch();
    }

    @When("I set criteria: {string}")
    public void i_set_criteria(String criteria) {
        homePage.setAdvancedSearchCriteria(criteria);
    }

    @When("I navigate to {string} category")
    public void i_navigate_to_category(String category) {
        homePage.selectCategory(category);
    }

    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        homePage.enterSearchTerm(searchTerm);
        homePage.clickSearchButton();
    }

    @When("I apply filter Brand {string}")
    public void i_apply_filter(String filter) {
        searchResultsPage.applyFilter(filter);
    }

    @When("I sort results by {string}")
    public void i_sort_results_by(String sortOption) {
        searchResultsPage.sortResultsBy(sortOption);
    }

    @When("I navigate to the second page of results")
    public void i_navigate_to_the_second_page_of_results() {
        searchResultsPage.goToPage(2);
    }

    @When("I click on the search bar")
    public void i_click_on_the_search_bar() {
        homePage.clickOnSearchBar();
    }

    @When("I use Tab to focus on the search bar")
    public void i_use_tab_to_focus_on_the_search_bar() {
        homePage.focusOnSearchBarWithKeyboard();
    }

    @When("I enter {string}")
    public void i_enter(String searchTerm) {
        homePage.enterSearchTerm(searchTerm);
    }

    @When("I press Enter")
    public void i_press_enter() {
        homePage.pressEnterKey();
    }

    @When("I perform a search for {string}")
    public void i_perform_a_search(String searchTerm) {
        homePage.enterSearchTerm(searchTerm);
        homePage.clickSearchButton();
    }

    @And("I click the search button")
    public void i_click_the_search_button() {
        homePage.clickSearchButton();
    }

    @Then("I should see results related to {string}")
    public void i_should_see_results_related_to(String searchTerm) {
        boolean hasResults = searchResultsPage.verifyResultsContain(searchTerm);
        Assert.assertTrue("Expected results not found for search term: " + searchTerm, hasResults);
    }

    @Then("I should see a message indicating no results were found")
    public void i_should_see_a_message_indicating_no_results_were_found() {
        boolean noResults = searchResultsPage.isNoResultsMessageDisplayed();
        Assert.assertTrue("Expected 'no results' message, but found results!", noResults);
    }

    @Then("I should see the categories page")
    public void i_should_see_the_categories_page() {
    boolean isCategoriesPageDisplayed = homePage.isCategoriesPageDisplayed();
    Assert.assertTrue("Expected categories page is not displayed.", isCategoriesPageDisplayed);
    }


    @Then("the input is handled gracefully without errors")
    public void the_input_is_handled_gracefully_without_errors() {
        boolean isHandledGracefully = searchResultsPage.isPageDisplayedCorrectly();
        Assert.assertTrue("The input was not handled gracefully.", isHandledGracefully);
    }

    @Then("the input is sanitized and no security breach occurs")
    public void the_input_is_sanitized_and_no_security_breach_occurs() {
        boolean isSecure = searchResultsPage.isPageSecure();
        Assert.assertTrue("Potential security breach detected!", isSecure);
    }

    @Then("the input is sanitized and the script is not executed")
    public void the_input_is_sanitized_and_the_script_is_not_executed() {
        boolean isScriptExecuted = searchResultsPage.isScriptExecuted();
        Assert.assertFalse("Script was executed, potential XSS vulnerability!", isScriptExecuted);
    }

    @Then("autocomplete suggestions like {string} appear")
    public void autocomplete_suggestions_like_appear(String expectedSuggestion) {
        boolean suggestionAppears = homePage.isSuggestionDisplayed(expectedSuggestion);
        Assert.assertTrue("Autocomplete suggestion not displayed.", suggestionAppears);
    }

    @Then("I should see additional relevant products")
    public void i_should_see_additional_relevant_products() {
        boolean hasResults = searchResultsPage.verifyResultsAreDisplayed();
        Assert.assertTrue("No additional products found on the second page.", hasResults);
    }

    @Then("I should see only {string} cameras")
    public void i_should_see_only_canon_cameras(String brand) {
        boolean onlyBrand = searchResultsPage.verifyResultsContain(brand);
        Assert.assertTrue("Results contain items other than" + brand + "cameras.", onlyBrand);
    }

    @Then("products are sorted accordingly")
    public void products_are_sorted_accordingly() {
        boolean isSorted = searchResultsPage.areResultsSortedByPriceLowToHigh();
        Assert.assertTrue("Products are not sorted by total price (item + shipping) from low to high.", isSorted);
    }

    @Then("the search is case-insensitive and relevant products are displayed")
    public void the_search_is_case_insensitive_and_relevant_products_are_displayed() {
        boolean hasResults = searchResultsPage.verifyResultsContain("Apple Watch");
        Assert.assertTrue("Relevant products are not displayed.", hasResults);
    }

    @Then("I should be suggested results for {string}")
    public void i_should_be_suggested(String suggestion) {
        boolean isSuggestionDisplayed = searchResultsPage.isSuggestionDisplayed(suggestion);
        Assert.assertTrue("Suggestion '" + suggestion + "' was not displayed.", isSuggestionDisplayed);
    }

    @Then("products listed under {string} are displayed")
    public void products_listed_under_are_displayed(String category) {
        boolean isCategoryDisplayed = searchResultsPage.isCategoryDisplayed(category);
        Assert.assertTrue("Products under category '" + category + "' are not displayed.", isCategoryDisplayed);
    }

    @Then("I should see products matching the criteria")
    public void i_should_see_products_matching_the_criteria() {
        boolean matchesCriteria = searchResultsPage.verifyResultsMatchCriteria();
        Assert.assertTrue("Products do not match the criteria.", matchesCriteria);
    }

    @Then("I should see tablets within Electronics")
    public void i_should_see_tablets_within_electronics() {
        boolean isInCategory = searchResultsPage.isCategoryDisplayed("Electronics");
        Assert.assertTrue("Results are not within the 'Electronics' category.", isInCategory);
    }

    @Then("I should see a list of recent search terms")
    public void i_should_see_a_list_of_recent_search_terms() {
        boolean recentSearchesDisplayed = homePage.areRecentSearchesDisplayed();
        Assert.assertTrue("Recent searches are not displayed.", recentSearchesDisplayed);
    }

    @Then("I should see products relevant to the term")
    public void i_should_see_products_relevant_to_the_term() {
        boolean hasResults = searchResultsPage.verifyResultsAreDisplayed();
        Assert.assertTrue("No products are displayed for the multi-language term.", hasResults);
    }

    @Then("the search executes without mouse interaction")
    public void the_search_executes_without_mouse_interaction() {
        boolean searchExecuted = searchResultsPage.verifySearchExecuted();
        Assert.assertTrue("Search did not execute via keyboard interaction.", searchExecuted);
    }

    @Then("the search functionality works seamlessly on mobile")
    public void the_search_functionality_works_seamlessly_on_mobile() {
        boolean hasResults = searchResultsPage.verifyResultsAreDisplayed();
        Assert.assertTrue("Search functionality is not working properly on mobile.", hasResults);
    }
}
