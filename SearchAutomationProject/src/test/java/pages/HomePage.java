package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators for desktop and mobile views
    By desktopSearchBarLocator = By.id("gh-ac");
    By mobileSearchBarLocator = By.id("kw"); // Mobile search bar locator
    By searchButtonLocator = By.id("gh-btn");

    // Other locators
    By advancedSearchLinkLocator = By.linkText("Advanced");
    By autocompleteSuggestionsLocator = By.cssSelector(".ui-autocomplete li");
    By recentSearchesLocator = By.cssSelector(".saved-searches");

    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Initialize elements if you still have other @FindBy annotations
        PageFactory.initElements(driver, this);
    }

    public void enterSearchTerm(String searchTerm){
        WebElement searchBarElement = getSearchBarElement();
        searchBarElement.clear();
        searchBarElement.sendKeys(searchTerm);
    }

    public void enterPartialSearchTerm(String partialTerm){
        WebElement searchBarElement = getSearchBarElement();
        searchBarElement.clear();
        for (char ch : partialTerm.toCharArray()) {
            searchBarElement.sendKeys(Character.toString(ch));
            try {
                Thread.sleep(500); // Wait to simulate typing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickSearchButton(){
        WebElement searchButtonElement = getSearchButtonElement();
        searchButtonElement.click();
    }

    public void clickAdvancedSearch(){
        WebElement advancedSearchLink = wait.until(ExpectedConditions.elementToBeClickable(advancedSearchLinkLocator));
        advancedSearchLink.click();
    }

    public void setAdvancedSearchCriteria(String criteria) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    
        try {
            // --- Set Maximum Price to 500 ---
            WebElement maxPriceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("_udhi")
            ));
            scrollIntoView(maxPriceInput); // Ensure the element is visible
            maxPriceInput.clear();
            maxPriceInput.sendKeys("500");
    
            // // --- Select 'Buy It Now' Option ---
            // WebElement buyItNowRadio = wait.until(ExpectedConditions.elementToBeClickable(
            //     By.cssSelector("input[data-testid='s0-1-17-6[3]-[2]-LH_BIN']")
            // ));
            // scrollIntoView(buyItNowRadio); // Scroll to the element
            // if (!buyItNowRadio.isSelected()) {
            //     buyItNowRadio.click();
            // }
    
            // // --- Select 'New' Condition ---
            // WebElement newRadio = wait.until(ExpectedConditions.elementToBeClickable(
            //     By.cssSelector("input[data-testid='s0-1-17-6[4]-[0]-LH_ItemCondition']")
            // ));
            // scrollIntoView(newRadio); // Scroll to the element
            // if (!newRadio.isSelected()) {
            //     newRadio.click();
            // }
    
            // --- Click the 'Search' Button ---
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn--primary[type='submit']")
            ));
            scrollIntoView(searchButton); // Ensure visibility
            searchButton.click();
    
        } catch (TimeoutException e) {
            System.out.println("An element was not found within the wait time.");
            e.printStackTrace();
            throw new RuntimeException("Failed to set advanced search criteria due to timeout.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An unexpected error occurred while setting advanced search criteria.");
        }
    }
    
    private void scrollIntoView(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        } catch (Exception e) {
            System.out.println("Unable to scroll to the element: " + element);
            e.printStackTrace();
        }
    }
    
    public void selectCategory(String category) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Locate the category element
            WebElement categoryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(category)));

            // Hover over the category element to make the subcategories visible
            Actions actions = new Actions(driver);
            actions.moveToElement(categoryElement).perform();

        } catch (TimeoutException e) {
            throw new NoSuchElementException("Category '" + category + "' not found or not visible within the wait time.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while hovering over the category '" + category + "'.");
        }
    }

    public void clickOnSearchBar(){
        WebElement searchBarElement = getSearchBarElement();
        searchBarElement.click();
    }

    public void focusOnSearchBarWithKeyboard() {
        // Ensure the focus starts from a known element, like the body
        driver.findElement(By.tagName("body")).click();

        // Create an instance of Actions
        Actions actions = new Actions(driver);

        // Use Keys.TAB to navigate to the search bar
        // Adjust the number of TAB keys as per the page structure
        for (int i = 0; i < 5; i++) {
            actions.sendKeys(Keys.TAB).perform();
            try {
                WebElement searchBarElement = driver.switchTo().activeElement();
                if (searchBarElement.equals(getSearchBarElement())) {
                    break;
                }
            } catch (Exception e) {
                // Continue tabbing
            }
        }
    }

    public void pressEnterKey(){
        WebElement searchBarElement = getSearchBarElement();
        searchBarElement.sendKeys(Keys.ENTER);
    }

    public boolean isSuggestionDisplayed(String expectedSuggestion){
        try {
            // Wait for suggestions to be visible
            List<WebElement> suggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(autocompleteSuggestionsLocator));
            for (WebElement suggestion : suggestions) {
                if (suggestion.getText().equalsIgnoreCase(expectedSuggestion)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSearchPromptDisplayed(){
        try {
            // Assuming eBay displays an error message when search is empty
            WebElement errorMessage = driver.findElement(By.cssSelector(".error-message"));
            return errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean areRecentSearchesDisplayed() {
        try {
            // Focus on the search bar to trigger the dropdown
            WebElement searchBarElement = getSearchBarElement();
            searchBarElement.click();

            // Wait for the recent searches list to be visible
            WebElement recentSearchesList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("ul.ghAC_newui[id='ui-id-1']")
            ));

            // Verify if the recent searches list is displayed
            return recentSearchesList.isDisplayed();

        } catch (TimeoutException | NoSuchElementException e) {
            // The recent searches list is not displayed
            return false;
        }
    }

    public boolean isCategoriesPageDisplayed() {
        try {
            // Check if the current URL contains "/n/all-categories"
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("/n/all-categories");
        } catch (Exception e) {
            return false;
        }
    }

    // Private helper methods to handle both desktop and mobile views

    private WebElement getSearchBarElement() {
        try {
            if (isElementVisible(mobileSearchBarLocator)) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(mobileSearchBarLocator));
            }
            return wait.until(ExpectedConditions.visibilityOfElementLocated(desktopSearchBarLocator));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Search bar not found on the page.");
        }
    }

    private WebElement getSearchButtonElement() {
        try {
            By mobileSearchButtonLocator = By.cssSelector("button.gh-search__submitbtn[aria-label='Search']");
            if (isElementVisible(mobileSearchButtonLocator)) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(mobileSearchButtonLocator));
            }
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchButtonLocator));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Search button not found on the page.");
        }
    }

    private boolean isElementVisible(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
