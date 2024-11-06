package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.HomePage;
import pages.SearchResultsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchSteps {

    WebDriver driver = Hooks.driver;
    HomePage homePage = new HomePage(driver);
    SearchResultsPage searchResultsPage = new SearchResultsPage(driver);

    @Given("I am on the eBay homepage")
    public void i_am_on_the_ebay_homepage() {
        driver.get("https://www.ebay.com");
    }

    @When("I enter {string} in the search bar")
    public void i_enter_in_the_search_bar(String searchTerm) {
        homePage.enterSearchTerm(searchTerm);
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
    // @Then("I should see results related to \"{string}\"")
    // public void i_should_see_results_related_to(String searchTerm) {
    //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    //     wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".s-item__title")));

    //     SearchResultsPage resultsPage = new SearchResultsPage(driver);
    //     Assert.assertTrue("Expected results not found for search term: " + searchTerm,
    //             resultsPage.verifyResultsContain(searchTerm));
    // }


    @Then("I should see a message indicating no results were found")
    public void i_should_see_a_message_indicating_no_results_were_found() {
        boolean noResults = searchResultsPage.isNoResultsMessageDisplayed();
        Assert.assertTrue("Expected no results to be displayed, but results were found!", noResults);
    }

}
