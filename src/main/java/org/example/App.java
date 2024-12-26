package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class App {
    public static void main(String[] args) {
        // Set up Geckodriver automatically
        System.setProperty("webdriver.gecko.driver", "D:\\SeleniumDriver\\Firefoxdriver\\geckodriver.exe");

        // Initialize the FirefoxDriver
        WebDriver driver = new FirefoxDriver();

        // Navigate to a webpage
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Close the browser
        driver.quit();
    }
}






