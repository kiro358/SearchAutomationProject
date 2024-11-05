package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import io.cucumber.java.en.*;
import pages.HomePage;
import pages.SearchResultsPage;

public class SearchSteps {

    WebDriver driver = Hooks.driver;
    HomePage homePage;
    SearchResultsPage searchResultsPage;

    @Given("I am on the homepage")
    public void i_am_on_the_homepage() {
        driver.get("https://www.example.com"); // Replace with your application's URL
        homePage = new HomePage(driver);
    }

    @When("I enter {string} in the search bar")
    public void i_enter_in_the_search_bar(String searchTerm) {
        homePage.enterSearchTerm(searchTerm);
    }

    @And("I click the search button")
    public void i_click_the_search_button() {
        searchResultsPage = homePage.clickSearchButton();
    }

    @Then("I should see {string}")
    public void i_should_see(String expectedResult) {
        boolean result = searchResultsPage.verifySearchResults(expectedResult);
        assert result : "Expected result not found!";
    }
}
