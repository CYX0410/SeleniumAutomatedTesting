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

import static org.bouncycastle.oer.its.ieee1609dot2.EndEntityType.app;

@Listeners(CustomTestListener.class)
public class TestPhoneNumber {
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
    public void testPhoneWithValidData() {
        WebElement phoneInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/div[2]/input")));

        phoneInput.sendKeys(Keys.CONTROL + "a");  // Select all text (Ctrl + A)
        phoneInput.sendKeys(Keys.BACK_SPACE);    // Clear the input field
        phoneInput.sendKeys("0172948277");
        System.out.println("Valid phone input test started");
        Assert.assertEquals(phoneInput.getAttribute("value"), "0172948277", "Valid phone number was not accepted!");
        System.out.println("Valid phone input test passed");

        // Save Changes
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
        // Final Validation or Confirmation Message (if applicable)
        // Add assertions here if there's any confirmation dialog or message after saving
    }

    @Test
    public void testPhoneWithInvalidDataSpecialCharacters() {
        WebElement phoneInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/div[2]/input")));

        phoneInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        phoneInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        phoneInput.sendKeys("*^!");
        System.out.println("Testing invalid phone input: *^!");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid phone input: *^!");
        System.out.println("Invalid phone test passed for: *^!");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testPhoneWithInvalidDataAlphabetic() {
        WebElement phoneInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/div[2]/input")));
        phoneInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        phoneInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        phoneInput.sendKeys("opqrstu");
        System.out.println("Testing invalid phone input: opqrstu");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid phone input: opqrstu");
        System.out.println("Invalid phone test passed for: opqrstu");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testPhoneWithInvalidDataAlphaNumeric() {
        WebElement phoneInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/div[2]/input")));

        phoneInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        phoneInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        phoneInput.sendKeys("017www166");
        System.out.println("Testing invalid phone input: 017www166");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid phone input: 017www166");
        System.out.println("Invalid phone test passed for: 017www166");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testPhoneWithEmptyData() {
        WebElement phoneInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/div[2]/input")));
        phoneInput.sendKeys(Keys.CONTROL + "a");
        phoneInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("Testing empty phone input");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement errorMessage = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[4]/div/span")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for empty phone.");
            System.out.println("Empty phone input test passed");
        } catch (TimeoutException e) {
            Assert.fail("Error message not displayed for empty phone within 4 seconds.");
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

