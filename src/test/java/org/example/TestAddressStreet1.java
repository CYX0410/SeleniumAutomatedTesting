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
public class TestAddressStreet1 {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "D:\\SeleniumDriver\\Firefoxdriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // -------------------------
        // Perform login steps here:
        // -------------------------
        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameInput.sendKeys("Admin");

        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordInput.sendKeys("admin123");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        loginButton.click();

        // -------------------------
        // Navigate to the section:
        // -------------------------
        WebElement adminMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/web/index.php/admin/viewAdminModule']")));
        adminMenu.click();

        WebElement organizationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'oxd-topbar-body-nav-tab-item') and contains(text(),'Organization')]")));
        organizationMenu.click();

        WebElement generalInfoOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-link') and text()='General Information']")));
        generalInfoOption.click();

        // Enable the Edit feature
        WebElement editToggle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'oxd-switch-input')]")));
        editToggle.click();
    }

    @Test
    public void testAddressStreet1WithValidData() {
        WebElement addressStreet1Input = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[1]/div/div[2]/input")
        ));

        // Clear any existing text
        addressStreet1Input.sendKeys(Keys.CONTROL + "a");
        addressStreet1Input.sendKeys(Keys.BACK_SPACE);

        // Enter valid data
        String validAddress = "123 Main St.";
        addressStreet1Input.sendKeys(validAddress);
        System.out.println("Testing valid Address Street 1 input: " + validAddress);

        // Optional: Assert that the field has the expected value
        Assert.assertEquals(
                addressStreet1Input.getAttribute("value"),
                validAddress,
                "Valid Address Street 1 input was not accepted!"
        );
        System.out.println("Valid data test passed for Address Street 1");

        // Click Save
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")
        ));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    @Test
    public void testAddressStreet1WithInvalidData() {
        WebElement addressStreet1Input = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[1]/div/div[2]/input")
        ));

        // Clear any existing text
        addressStreet1Input.sendKeys(Keys.CONTROL + "a");
        addressStreet1Input.sendKeys(Keys.BACK_SPACE);

        // Enter invalid data (e.g., numeric only, special chars, blank, etc.)
        String invalidInput = "123456789";
        addressStreet1Input.sendKeys(invalidInput);
        System.out.println("Testing invalid Address Street 1 input: " + invalidInput);

        // Check for presence of an error message
        boolean isErrorMessagePresent = driver.findElements(By.xpath(
                "//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[1]/div/span"
        )).size() > 0;

        // If error message is present, assert it's displayed; otherwise fail
        if (isErrorMessagePresent) {
            WebElement errorMessage = driver.findElement(By.xpath(
                    "//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[1]/div/span"
            ));
            Assert.assertTrue(
                    errorMessage.isDisplayed(),
                    "Error message is not displayed for invalid Address Street 1 input."
            );
            System.out.println("Invalid data test passed for Address Street 1");
        } else {
            System.out.println("Test Failed: Error message not displayed for invalid data in Address Street 1.");
            Assert.fail("Error message not displayed for invalid data in Address Street 1.");
        }

        // Click Save
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[7]/button")
        ));
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

