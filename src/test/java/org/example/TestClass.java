package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestClass {
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
        // Wait for the Edit toggle and click it
        WebElement editToggle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'oxd-switch-input')]")));
        editToggle.click();
    }

    @Test
    public void testOrganizationNameWithValidData() {
        // Step 6: Enter Valid Organization Name
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));// Locate the input field using XPath
        WebElement orgNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/div[2]/input")));

        orgNameInput.sendKeys(Keys.CONTROL + "a");  // Select all text (Ctrl + A)
        orgNameInput.sendKeys(Keys.BACK_SPACE);    // Clear the input field
        orgNameInput.sendKeys("Deloitte’s 88 Sdn Bhd");
        System.out.println("Valid input test started");
        Assert.assertEquals(orgNameInput.getAttribute("value"), "Deloitte’s 88 Sdn Bhd", "Valid organization name was not accepted!");
        System.out.println("Valid input test passed");

        // Save Changes
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
        // Final Validation or Confirmation Message (if applicable)
        // Add assertions here if there's any confirmation dialog or message after saving
    }

    @Test
    public void testOrganizationNameWithSymbolOnlyData(){
        WebElement orgNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/div[2]/input")));
        String[] invalidSymbols = {"@$%", "##!!"};

        for (String input : invalidSymbols) {
            orgNameInput.sendKeys(Keys.CONTROL + "a");  // Select all text
            orgNameInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
            orgNameInput.sendKeys(input);
            System.out.println("Testing invalid symbol input: " + input);
            Assert.assertNotEquals(orgNameInput.getAttribute("value"), input, "Invalid symbol input accepted: " + input);
            System.out.println("Symbol test passed for: " + input);

            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
            saveButton.click();
            System.out.println("Save button clicked");
        }
    }

    @Test
    public void testOrganizationNameWithNumberOnlyData(){
        WebElement orgNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/div[2]/input")));
        String[] invalidNumbers = {"15652", "123456"};

        for (String input : invalidNumbers) {
            orgNameInput.sendKeys(Keys.CONTROL + "a");  // Select all text
            orgNameInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
            orgNameInput.sendKeys(input);
            System.out.println("Testing invalid numeric input: " + input);
            Assert.assertNotEquals(orgNameInput.getAttribute("value"), input, "Invalid numeric input accepted: " + input);
            System.out.println("Numeric test passed for: " + input);

            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
            saveButton.click();
            System.out.println("Save button clicked");
        }
    }

    @Test
    public void testOrganizationNameWithEmptyData(){
        WebElement orgNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/div[2]/input")));
        orgNameInput.sendKeys(Keys.CONTROL + "a");
        orgNameInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("Testing empty input");
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for empty Organization Name.");
        System.out.println("Empty input test passed");

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



