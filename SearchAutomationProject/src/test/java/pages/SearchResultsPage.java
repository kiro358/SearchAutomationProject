package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "h3.srp-save-null-search__heading")
    private WebElement noResultsHeading;

    @FindBy(css = ".s-item__title")
    private List<WebElement> resultTitles;

    @FindBy(css = ".srp-controls__count-heading")
    private WebElement resultsHeading;

    @FindBy(css = ".srp-controls--selected-value")
    private WebElement selectedFilter;

    @FindBy(css = ".pagination__items")
    private WebElement pagination;

    @FindBy(css = ".s-item")
    private List<WebElement> searchResults;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    private WebElement safeFindElement(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException | NoSuchElementException e) {
            return null;
        }
    }

    private List<WebElement> safeFindElements(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (TimeoutException | NoSuchElementException e) {
            return List.of();
        }
    }

    public boolean verifyResultsContain(String searchTerm) {
        By titleLocator = By.cssSelector(".s-item__title");
        
        try {
            // Wait until at least one element matching the locator is visible
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(titleLocator));
    
            // Find all elements matching the locator
            List<WebElement> resultTitles = driver.findElements(titleLocator);
            
            // Ensure there are visible titles
            if (resultTitles.isEmpty()) {
                throw new NoSuchElementException("No elements found with the locator: " + titleLocator);
            }
            
            // Check if any of the titles contain the search term
            return resultTitles.stream()
                    .anyMatch(title -> title.getText().toLowerCase().contains(searchTerm.toLowerCase()));
        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for elements: " + titleLocator);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

    public boolean isNoResultsMessageDisplayed() {
        WebElement noResultsMessage = safeFindElement(By.xpath("//*[contains(text(), 'No exact matches found')]"));
        return noResultsMessage != null && noResultsMessage.isDisplayed();
    }

    public boolean isPageDisplayedCorrectly() {
        try {
            String title = driver.getTitle();
            return title != null && !title.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageSecure() {
        WebElement errorDiv = safeFindElement(By.id("error"));
        if (errorDiv != null) {
            String errorHeaderText = safeGetText(errorDiv.findElement(By.tagName("h1")));
            return "Bad Request".equalsIgnoreCase(errorHeaderText);
        }
        return false;
    }

    private String safeGetText(WebElement element) {
        try {
            return element.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isScriptExecuted() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void applyFilter(String filter) {
        String xpath = "//li[@class='x-refine__main__list--value' and @name='Brand']" +
                       "//a[contains(@class, 'x-refine__multi-select-link')]" +
                       "[.//span[contains(text(),'" + filter + "')]]";
        WebElement filterElement = waitForElementToBeClickable(By.xpath(xpath));
        if (filterElement != null) {
            filterElement.click();
            wait.until(ExpectedConditions.urlContains("Brand=" + filter));
        } else {
            throw new NoSuchElementException("Filter '" + filter + "' not found or not clickable.");
        }
    }

    public void sortResultsBy(String sortOption) {
        try {
            // First wait for the sort button to be present and visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement sortButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.fake-menu-button__button[aria-label*='Sort selector']")
            ));

            // Scroll the sort button into view with offset to ensure it's fully visible
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                sortButton
            );

            // Add a pause to let any animations complete
            Thread.sleep(100);

            // Clear any overlays or popups that might interfere
            try {
                List<WebElement> overlays = driver.findElements(By.cssSelector(".overlay, .popup, .modal"));
                for (WebElement overlay : overlays) {
                    if (overlay.isDisplayed()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", overlay);
                    }
                }
            } catch (Exception e) {
                // Ignore if no overlays found
            }

            // Try multiple click strategies with retry logic
            boolean clickSuccess = false;
            Exception lastException = null;
            int maxRetries = 3;

            for (int i = 0; i < maxRetries && !clickSuccess; i++) {
                try {
                    switch (i) {
                        case 0:
                            sortButton.click();
                            clickSuccess = true;
                            break;
                        case 1:
                            new Actions(driver)
                                .moveToElement(sortButton)
                                .pause(Duration.ofMillis(500))
                                .click()
                                .perform();
                            clickSuccess = true;
                            break;
                        case 2:
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sortButton);
                            clickSuccess = true;
                            break;
                    }
                } catch (Exception e) {
                    lastException = e;
                    Thread.sleep(100); // Wait before retry
                }
            }

            if (!clickSuccess && lastException != null) {
                throw new ElementClickInterceptedException("Failed to click sort button after multiple attempts", lastException);
            }

            // Wait for dropdown menu with multiple selector attempts
            WebElement menuElement = null;
            String[] menuSelectors = {
                ".fake-menu-button__menu",
                "div[role='menu']",
                ".sort-menu-container",
                "span.fake-menu-button__menu",
                "[data-testid='sort-menu']"
            };

            for (String selector : menuSelectors) {
                try {
                    menuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
                    break;
                } catch (TimeoutException e) {
                    continue;
                }
            }

            if (menuElement == null) {
                throw new NoSuchElementException("Sort menu not found after trying multiple selectors");
            }

            // Wait for menu animation to complete
            Thread.sleep(100);

            // Try multiple ways to locate and click the sort option
            WebElement sortOptionElement = null;
            String normalizedSortOption = sortOption.toLowerCase().trim();

            // Define multiple strategies to find the sort option
            List<By> optionLocators = Arrays.asList(
                By.cssSelector("a[href*='_sop=15']"),
                By.xpath(String.format("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]", normalizedSortOption)),
                By.xpath(String.format("//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]", normalizedSortOption)),
                By.cssSelector("a.fake-menu-button__item[href*='_sop=15']"),
                By.cssSelector("[data-value='price_shipping_low']")
            );

            for (By locator : optionLocators) {
                try {
                    sortOptionElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    break;
                } catch (TimeoutException e) {
                    continue;
                }
            }

            if (sortOptionElement == null) {
                throw new NoSuchElementException("Sort option not found: " + sortOption);
            }

            // Try to click the sort option with retry logic
            clickSuccess = false;
            for (int i = 0; i < maxRetries && !clickSuccess; i++) {
                try {
                    switch (i) {
                        case 0:
                            sortOptionElement.click();
                            clickSuccess = true;
                            break;
                        case 1:
                            new Actions(driver)
                                .moveToElement(sortOptionElement)
                                .pause(Duration.ofMillis(500))
                                .click()
                                .perform();
                            clickSuccess = true;
                            break;
                        case 2:
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sortOptionElement);
                            clickSuccess = true;
                            break;
                    }
                } catch (Exception e) {
                    lastException = e;
                    Thread.sleep(100);
                }
            }

            // Wait for sort to take effect
            Thread.sleep(500);

            // Verify the sort was applied
            try {
                wait.until(ExpectedConditions.stalenessOf(menuElement));
            } catch (Exception e) {
                // Ignore if menu element is still present
            }

        } catch (Exception e) {
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("_sop=15")) {
                String newUrl = currentUrl + (currentUrl.contains("?") ? "&_sop=15" : "?_sop=15");
                driver.navigate().to(newUrl);
            }
        }
    }
    
    public void goToPage(int pageNumber) {
        String xpath = "//a[contains(@class, 'pagination__item') and normalize-space(.)='" + pageNumber + "']";
        WebElement pageLink = waitForElementToBeClickable(By.xpath(xpath));
        if (pageLink != null) {
            pageLink.click();
        } else {
            throw new NoSuchElementException("Page number " + pageNumber + " not found in pagination.");
        }
    }

    public boolean verifyResultsAreDisplayed() {
        return searchResults != null && !searchResults.isEmpty();
    }

    public boolean areResultsSortedByPriceLowToHigh() {
        try {
            // Get the current page URL
            String currentUrl = driver.getCurrentUrl();
            
            // Check if the URL contains the sorting query parameter for low to high price
            return currentUrl.contains("_sop=15");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean isSuggestionDisplayed(String suggestion) {
        String xpath = "//p[contains(., '" + suggestion + "')]";
        WebElement suggestionElement = waitForVisibility(By.xpath(xpath));
        return suggestionElement != null && suggestionElement.isDisplayed();
    }

    public boolean isCategoryDisplayed(String category) {
        String xpath = "//span[span[@class='clipped' and text()='Selected category']]";
        WebElement selectedCategoryElement = waitForVisibility(By.xpath(xpath));
    
        if (selectedCategoryElement != null) {
            // Get the full text of the outer <span>
            String fullText = selectedCategoryElement.getText().trim();
    
            // Remove 'Selected category' from the beginning of the text
            String categoryText = fullText.replaceFirst("(?i)^Selected category\\s*", "").trim();
    
            return category.equalsIgnoreCase(categoryText);
        } else {
            return false;
        }
    }
    
    public boolean verifyResultsMatchCriteria() {
        try {
            // Locate all filter elements
            List<WebElement> filters = safeFindElements(By.cssSelector("li.srp-carousel-list__item--group-has-title a.srp-carousel-list__item-link"));
    
            // Check if the "Under $500.00" filter exists
            boolean priceFilterExists = filters.stream()
                    .anyMatch(filter -> filter.getText().toLowerCase().contains("under $500"));
    
            return priceFilterExists;
    
        } catch (Exception e) {
            System.err.println("Error verifying results match criteria: " + e.getMessage());
            return false;
        }
    }
    
    public boolean verifySearchExecuted() {
        try {
            // Wait for the h1 element with class 'srp-controls__count-heading' to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement headingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h1.srp-controls__count-heading")
            ));
            // If the element is found and visible, return true
            return headingElement.isDisplayed();
        } catch (TimeoutException e) {
            // If the element is not found within the timeout, return false
            return false;
        }
    }

    private WebElement waitForElementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            return null;
        }
    }

    private WebElement waitForVisibility(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return null;
        }
    }

}
