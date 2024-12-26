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
public class TestTaxID {
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
    public void testTaxIDWithValidData() {
        WebElement taxIDInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/input")));

        taxIDInput.sendKeys(Keys.CONTROL + "a");  // Select all text (Ctrl + A)
        taxIDInput.sendKeys(Keys.BACK_SPACE);    // Clear the input field
        taxIDInput.sendKeys("20033");
        System.out.println("Valid Tax ID input test started");
        Assert.assertEquals(taxIDInput.getAttribute("value"), "20033", "Valid Tax ID was not accepted!");
        System.out.println("Valid Tax ID input test passed");

        // Save Changes
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
        // Final Validation or Confirmation Message (if applicable)
        // Add assertions here if there's any confirmation dialog or message after saving
    }

    @Test
    public void testTaxIDWithSpecialCharacters() {
        WebElement taxIDInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/input")));
        String invalidData = "&@%";

        taxIDInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        taxIDInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        taxIDInput.sendKeys(invalidData);
        System.out.println("Testing invalid Tax ID input: " + invalidData);
        Assert.assertNotEquals(taxIDInput.getAttribute("value"), invalidData, "Invalid Tax ID input accepted: " + invalidData);
        System.out.println("Invalid Tax ID test passed for: " + invalidData);

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testTaxIDWithAlphabeticData() {
        WebElement taxIDInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/input")));
        String invalidData = "efghij";

        taxIDInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        taxIDInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        taxIDInput.sendKeys(invalidData);
        System.out.println("Testing invalid Tax ID input: " + invalidData);
        Assert.assertNotEquals(taxIDInput.getAttribute("value"), invalidData, "Invalid Tax ID input accepted: " + invalidData);
        System.out.println("Invalid Tax ID test passed for: " + invalidData);

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testTaxIDWithAlphaNumericData() {
        WebElement taxIDInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/input")));
        String invalidData = "20yy67";

        taxIDInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        taxIDInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        taxIDInput.sendKeys(invalidData);
        System.out.println("Testing invalid Tax ID input: " + invalidData);
        Assert.assertNotEquals(taxIDInput.getAttribute("value"), invalidData, "Invalid Tax ID input accepted: " + invalidData);
        System.out.println("Invalid Tax ID test passed for: " + invalidData);

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testTaxIDWithEmptyData() {
        WebElement taxIDInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/input")));
        taxIDInput.sendKeys(Keys.CONTROL + "a");
        taxIDInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("Testing empty Tax ID input");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement errorMessage = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/span")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for empty Tax ID.");
            System.out.println("Empty Tax ID input test passed");
        } catch (TimeoutException e) {
            Assert.fail("Error message not displayed for empty Tax ID within 4 seconds.");
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