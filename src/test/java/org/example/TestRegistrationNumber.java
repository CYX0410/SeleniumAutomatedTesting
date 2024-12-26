package org.example;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

@Listeners(CustomTestListener.class)
public class TestRegistrationNumber {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "D:\\SeleniumDriver\\Firefoxdriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Initialize WebDriverWait with 30 seconds

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Step 2: Log in to OrangeHRM as Admin
        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameInput.sendKeys("Admin");

        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordInput.sendKeys("admin123");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        loginButton.click();

        // Step 3: Navigate to the Admin Section
        WebElement adminMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/web/index.php/admin/viewAdminModule']")));
        adminMenu.click();

        // Step 4: Locate Organization > General Information
        WebElement organizationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'oxd-topbar-body-nav-tab-item') and contains(text(),'Organization')]")));
        organizationMenu.click();

        WebElement generalInfoOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-link') and text()='General Information']")));
        generalInfoOption.click();

        // Step 5: Activate the Edit Feature by Clicking the Edit Button
        WebElement editToggle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'oxd-switch-input')]")));
        editToggle.click();
    }

    @Test
    public void testRegistrationNumberWithValidData() {
        // Step 6: Enter Valid Registration Number
        WebElement regNumberInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/input")));

        regNumberInput.sendKeys(Keys.CONTROL + "a");  // Select all text (Ctrl + A)
        regNumberInput.sendKeys(Keys.BACK_SPACE);    // Clear the input field
        regNumberInput.sendKeys("9933188");
        System.out.println("Valid Registration Number input test started");
        Assert.assertEquals(regNumberInput.getAttribute("value"), "9933188", "Valid Registration Number was not accepted!");
        System.out.println("Valid Registration Number input test passed");

        // Save Changes
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
        // Final Validation or Confirmation Message (if applicable)
        // Add assertions here if there's any confirmation dialog or message after saving
    }
    @Test
    public void testRegistrationWithDataSpecialCharacters() {
        WebElement regNumberInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/input")));
        String invalidData = "##@";

        regNumberInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        regNumberInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        regNumberInput.sendKeys(invalidData);
        System.out.println("Testing invalid registration number input: " + invalidData);
        Assert.assertNotEquals(regNumberInput.getAttribute("value"), invalidData, "Invalid registration number input accepted: " + invalidData);
        System.out.println("Invalid registration number test passed for: " + invalidData);

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testRegistrationWithDataAlphabetic() {
        WebElement regNumberInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/input")));
        String invalidData = "abc";

        regNumberInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        regNumberInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        regNumberInput.sendKeys(invalidData);
        System.out.println("Testing invalid registration number input: " + invalidData);
        Assert.assertNotEquals(regNumberInput.getAttribute("value"), invalidData, "Invalid registration number input accepted: " + invalidData);
        System.out.println("Invalid registration number test passed for: " + invalidData);

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testRegistrationWithDataAlphaNumeric() {
        WebElement regNumberInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/input")));
        String invalidData = "99abc13";

        regNumberInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        regNumberInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        regNumberInput.sendKeys(invalidData);
        System.out.println("Testing invalid registration number input: " + invalidData);
        Assert.assertNotEquals(regNumberInput.getAttribute("value"), invalidData, "Invalid registration number input accepted: " + invalidData);
        System.out.println("Invalid registration number test passed for: " + invalidData);

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testRegistrationNumberWithEmptyData() {
        WebElement regNumberInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/input")));
        regNumberInput.sendKeys(Keys.CONTROL + "a");
        regNumberInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("Testing empty registration number input");

        boolean isErrorMessagePresent = driver.findElements(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/span")).size() > 0;

        if (isErrorMessagePresent) {
            WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/span"));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed for empty Registration Number.");
            System.out.println("Empty registration number input test passed");
        }
        else{
            System.out.println("Test Failed: Error message not displayed for empty Registration Number.");
            Assert.fail("Error message not displayed for empty Registration Number.");
        }
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}