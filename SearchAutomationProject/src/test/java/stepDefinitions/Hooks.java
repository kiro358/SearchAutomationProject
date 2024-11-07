package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        boolean isMobileTest = scenario.getSourceTagNames().contains("@Mobile");

        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        if (isMobileTest) {
            ChromeOptions options = new ChromeOptions();
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Pixel 7");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
            driver = new ChromeDriver(options);
        } else {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        
    }

    @After
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
