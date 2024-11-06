// package pages;

// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.FindBy;
// import org.openqa.selenium.support.PageFactory;

// public class HomePage {

//     WebDriver driver;

//     @FindBy(id = "twotabsearchtextbox")
//     WebElement searchBar;

//     @FindBy(id = "nav-search-submit-button")
//     WebElement searchButton;

//     public HomePage(WebDriver driver){
//         this.driver = driver;
//         PageFactory.initElements(driver, this);
//     }

//     public void enterSearchTerm(String searchTerm){
//         searchBar.clear();
//         searchBar.sendKeys(searchTerm);
//     }

//     public SearchResultsPage clickSearchButton(){
//         searchButton.click();
//         return new SearchResultsPage(driver);
//     }
// }
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;

    @FindBy(id = "gh-ac")  // eBay search bar ID
    WebElement searchBar;

    @FindBy(id = "gh-btn") // eBay search button ID
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
