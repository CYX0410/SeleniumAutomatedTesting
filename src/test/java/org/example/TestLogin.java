package org.example;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Listeners(CustomTestListener.class)
public class TestLogin {
    WebDriver driver;
    WebDriverWait wait;
    List<String> testResults = new ArrayList<>();

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "C:\\Tools\\geckodriver-v0.35.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Initialize WebDriverWait with 30 seconds

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    public void testValidUsername() {
        try {
            String validUsername = "Admin";
            WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
            usernameInput.sendKeys(validUsername);
            Assert.assertEquals(usernameInput.getAttribute("value"), validUsername, "Valid username was not accepted!");
            testResults.add("testValidUsername: Passed");
        } catch (Exception e) {
            testResults.add("testValidUsername: Failed");
        }
    }

    @Test
    public void testValidPassword() {
        try {
            String validPassword = "admin123";
            WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
            passwordInput.sendKeys(validPassword);
            Assert.assertEquals(passwordInput.getAttribute("value"), validPassword, "Valid password was not accepted!");
            testResults.add("testValidPassword: Passed");
        } catch (Exception e) {
            testResults.add("testValidPassword: Failed");
        }
    }

    @Test
    public void testInvalidUsernames() {
        try {
            String validPassword = "admin123";
            String[] invalidUsernames = {"#@$$#", "123443", ""};

            for (String invalidUsername : invalidUsernames) {
                WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
                usernameInput.sendKeys(Keys.CONTROL + "a");
                usernameInput.sendKeys(Keys.BACK_SPACE);
                usernameInput.sendKeys(invalidUsername);

                WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
                passwordInput.sendKeys(Keys.CONTROL + "a");
                passwordInput.sendKeys(Keys.BACK_SPACE);
                passwordInput.sendKeys(validPassword);

                WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
                loginButton.click();

                WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class,'oxd-alert-content-text')]")));
                Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid username: " + invalidUsername);
            }
            testResults.add("testInvalidUsernames: Passed");
        } catch (Exception e) {
            testResults.add("testInvalidUsernames: Failed");
        }
    }

    @Test
    public void testInvalidPasswords() {
        try {
            String validUsername = "Admin";
            String[] invalidPasswords = {"123456", "", "@#$#@@"};

            for (String invalidPassword : invalidPasswords) {
                WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
                usernameInput.sendKeys(Keys.CONTROL + "a");
                usernameInput.sendKeys(Keys.BACK_SPACE);
                usernameInput.sendKeys(validUsername);

                WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
                passwordInput.sendKeys(Keys.CONTROL + "a");
                passwordInput.sendKeys(Keys.BACK_SPACE);
                passwordInput.sendKeys(invalidPassword);

                WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
                loginButton.click();

                WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class,'oxd-alert-content-text')]")));
                Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid password: " + invalidPassword);
            }
            testResults.add("testInvalidPasswords: Passed");
        } catch (Exception e) {
            testResults.add("testInvalidPasswords: Failed");
        }
    }

    @AfterMethod
    public void clearFields() {
        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameInput.sendKeys(Keys.CONTROL + "a");
        usernameInput.sendKeys(Keys.BACK_SPACE);

        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordInput.sendKeys(Keys.CONTROL + "a");
        passwordInput.sendKeys(Keys.BACK_SPACE);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        printTestResults();
    }

    private void printTestResults() {
        System.out.println("| Test Case Method         | Result |");
        System.out.println("|--------------------------|--------|");
        for (String result : testResults) {
            String[] parts = result.split(": ");
            System.out.printf("| %-24s | %-6s |\n", parts[0], parts[1]);
        }
    }
}