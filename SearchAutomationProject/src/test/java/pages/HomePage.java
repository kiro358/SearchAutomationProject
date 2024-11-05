package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;

    @FindBy(name = "q") // Adjust the locator as per your application
    WebElement searchBar;

    @FindBy(css = "button[type='submit']") // Adjust the locator
    WebElement searchButton;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterSearchTerm(String searchTerm){
        searchBar.clear();
        searchBar.sendKeys(searchTerm);
    }

    public SearchResultsPage clickSearchButton(){
        searchButton.click();
        return new SearchResultsPage(driver);
    }
}
