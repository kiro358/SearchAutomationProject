package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp(){
        // Set the path to chromedriver executable
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // Update path
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
