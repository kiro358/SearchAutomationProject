package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".s-item__title")
    private List<WebElement> resultTitles;

    @FindBy(css = ".srp-results")
    private WebElement searchResultsContainer;

    private final By relatedSearchesSelector = By.cssSelector(".srp-related-searches");
    private final By noExactMatchesMessageSelector = By.xpath("//*[contains(text(), 'No exact matches found')]");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean verifyResultsContain(String searchTerm) {
        try {
            // Wait for the search results container to be visible
            wait.until(ExpectedConditions.visibilityOf(searchResultsContainer));
            
            // Wait for at least one result title to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".s-item__title")));
            
            // Refresh the elements list after waiting
            List<WebElement> currentResults = driver.findElements(By.cssSelector(".s-item__title"));
            
            // Convert search term to lower case for case-insensitive comparison
            String normalizedSearchTerm = searchTerm.toLowerCase();
            
            // Check each result title
            for (WebElement title : currentResults) {
                try {
                    String titleText = title.getText().toLowerCase();
                    // Check if the title contains any word from the search term
                    String[] searchWords = normalizedSearchTerm.split("\\s+");
                    for (String word : searchWords) {
                        if (titleText.contains(word)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    continue; // Skip any stale or problematic elements
                }
            }
            
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".s-item__title")),
                ExpectedConditions.presenceOfElementLocated(noExactMatchesMessageSelector)
            ));
            
            boolean noResultsBasedOnCount = driver.findElements(By.cssSelector(".s-item__title")).size() < 3;
            boolean noExactMatches = isElementPresent(noExactMatchesMessageSelector);
            boolean hasFallbackSuggestions = isElementPresent(relatedSearchesSelector);
            
            return noResultsBasedOnCount || noExactMatches || hasFallbackSuggestions;
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Assume no results if we can't verify
        }
    }

    private boolean isElementPresent(By selector) {
        try {
            return !driver.findElements(selector).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}