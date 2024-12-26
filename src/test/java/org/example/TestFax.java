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
public class TestFax {
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
    public void testFaxWithValidData() {
        WebElement faxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/div[2]/input")));

        faxInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        faxInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        faxInput.sendKeys("067239933");        // Valid fax input
        System.out.println("Testing valid fax input: 067239933");

        Assert.assertEquals(faxInput.getAttribute("value"), "067239933", "Valid fax input was not accepted!");
        System.out.println("Valid fax input test passed");

        // Save Changes
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testFaxWithInvalidDataSpecialCharacters() {
        WebElement faxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/div[2]/input")));

        faxInput.sendKeys(Keys.CONTROL + "a");
        faxInput.sendKeys(Keys.BACK_SPACE);
        faxInput.sendKeys("1)+>?");
        System.out.println("Testing invalid fax input: 1)+>?");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid fax input: 1)+>?");
        System.out.println("Invalid fax test passed for: 1)+>?");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testFaxWithInvalidDataAlphabetic() {
        WebElement faxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/div[2]/input")));

        faxInput.sendKeys(Keys.CONTROL + "a");
        faxInput.sendKeys(Keys.BACK_SPACE);
        faxInput.sendKeys("wxyz");
        System.out.println("Testing invalid fax input: wxyz");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid fax input: wxyz");
        System.out.println("Invalid fax test passed for: wxyz");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testFaxWithInvalidDataAlphaNumeric() {
        WebElement faxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/div[2]/input")));

        faxInput.sendKeys(Keys.CONTROL + "a");
        faxInput.sendKeys(Keys.BACK_SPACE);
        faxInput.sendKeys("06pm133");
        System.out.println("Testing invalid fax input: 06pm133");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid fax input: 06pm133");
        System.out.println("Invalid fax test passed for: 06pm133");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testFaxWithEmptyData() {
        WebElement faxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/div[2]/input")));

        faxInput.sendKeys(Keys.CONTROL + "a");
        faxInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("Testing empty fax input");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement errorMessage = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[1]/div/span")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for empty fax input.");
            System.out.println("Empty fax input test passed");
        } catch (TimeoutException e) {
            Assert.fail("Error message not displayed for empty fax input within 4 seconds.");
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
