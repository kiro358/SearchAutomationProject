package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultsPage {

    WebDriver driver;

    @FindBy(css = ".search-results") // Adjust the locator
    WebElement searchResults;

    @FindBy(css = ".no-results-message") // Adjust the locator
    WebElement noResultsMessage;

    public SearchResultsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean verifySearchResults(String expectedResult){
        if(expectedResult.contains("No results found")){
            return noResultsMessage.isDisplayed();
        } else {
            return searchResults.getText().contains(expectedResult);
        }
    }
}
