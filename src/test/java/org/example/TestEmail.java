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
public class TestEmail {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "D:\\SeleniumDriver\\Firefoxdriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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
    public void testEmailWithValidData() {
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/div[2]/input")));

        emailInput.sendKeys(Keys.CONTROL + "a");  // Select all text
        emailInput.sendKeys(Keys.BACK_SPACE);    // Clear the field
        emailInput.sendKeys("Ryuu123@gmail.com");        // Valid email input
        System.out.println("Testing valid email input: Ryuu123@gmail.com");

        Assert.assertEquals(emailInput.getAttribute("value"), "Ryuu123@gmail.com", "Valid email input was not accepted!");
        System.out.println("Valid email input test passed");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testEmailWithInvalidData_MissingAtSymbol() {
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/div[2]/input")));

        emailInput.sendKeys(Keys.CONTROL + "a");
        emailInput.sendKeys(Keys.BACK_SPACE);
        emailInput.sendKeys("Yumagmail.com");
        System.out.println("Testing invalid email input: Yumagmail.com");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid email input: Yumagmail.com");
        System.out.println("Invalid email test passed for: Yumagmail.com");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testEmailWithInvalidData_MissingDomain() {
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/div[2]/input")));

        emailInput.sendKeys(Keys.CONTROL + "a");
        emailInput.sendKeys(Keys.BACK_SPACE);
        emailInput.sendKeys("Tommy@gmail");
        System.out.println("Testing invalid email input: Tommy@gmail");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid email input: Tommy@gmail");
        System.out.println("Invalid email test passed for: Tommy@gmail");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testEmailWithInvalidData_SpecialCharacters() {
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/div[2]/input")));

        emailInput.sendKeys(Keys.CONTROL + "a");
        emailInput.sendKeys(Keys.BACK_SPACE);
        emailInput.sendKeys("~:!mia@hotmail.com");
        System.out.println("Testing invalid email input: ~:!mia@hotmail.com");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/span")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid email input: ~:!mia@hotmail.com");
        System.out.println("Invalid email test passed for: ~:!mia@hotmail.com");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testEmailWithEmptyData() {
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/div[2]/input")));

        emailInput.sendKeys(Keys.CONTROL + "a");
        emailInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("Testing empty email input");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement errorMessage = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[3]/div/span")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for empty email input.");
            System.out.println("Empty email input test passed");
        } catch (TimeoutException e) {
            Assert.fail("Error message not displayed for empty email input within 4 seconds.");
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

