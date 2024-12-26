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
public class TestAddressStreet2 {
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

        WebElement organizationMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'oxd-topbar-body-nav-tab-item') and contains(text(),'Organization')]")));
        organizationMenu.click();

        WebElement generalInfoOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-link') and text()='General Information']")));
        generalInfoOption.click();

        // -------------------------
        // Enable the Edit feature:
        // -------------------------
        WebElement editToggle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'oxd-switch-input')]")));
        editToggle.click();
    }

    // -----------------------------------------------------
    // TU26: Address Street 2 - Valid Data
    // -----------------------------------------------------
    @Test
    public void testAddressStreet2WithValidData() {
        // Step 1: Locate "Address Street 2" input field
        WebElement addressStreet2Input = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[2]/div/div[2]/input")));

        // Step 2: Clear any existing text
        addressStreet2Input.sendKeys(Keys.CONTROL + "a");
        addressStreet2Input.sendKeys(Keys.BACK_SPACE);

        // Step 3: Type the valid data
        String validAddress = "456 Broad Ave, Suite 6";
        addressStreet2Input.sendKeys(validAddress);
        System.out.println("Testing valid Address Street 2 input: " + validAddress);

        // (Optional) Assert that the field now has the expected value
        Assert.assertEquals(
                addressStreet2Input.getAttribute("value"),
                validAddress,
                "Valid Address Street 2 input was not accepted!"
        );
        System.out.println("Valid data test passed for Address Street 2");

        // Click Save
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
        saveButton.click();
        System.out.println("Save button clicked");
    }

    // -----------------------------------------------------
    // TU26: Address Street 2 - Invalid Data
    // -----------------------------------------------------
    @Test
    public void testAddressStreet2WithInvalidData() {
        // Step 1: Locate the "Address Street 2" input field
        WebElement addressStreet2Input = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[2]/div/div[2]/input")));

        // Invalid inputs to test (multiple patterns)
        String[] invalidInputs = {"789012", "@&^$%"};

        for (String invalidInput : invalidInputs) {
            // Clear the field before entering new text
            addressStreet2Input.sendKeys(Keys.CONTROL + "a");
            addressStreet2Input.sendKeys(Keys.BACK_SPACE);

            // Enter invalid data
            addressStreet2Input.sendKeys(invalidInput);
            System.out.println("Testing invalid Address Street 2 input: " + invalidInput);

            // Step 2: Check if error message is present
            boolean isErrorMessagePresent = driver.findElements(By.xpath(
                    "//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[2]/div/span"
            )).size() > 0;

            if (isErrorMessagePresent) {
                // Now retrieve the element and confirm it's displayed
                WebElement errorMessage = driver.findElement(By.xpath(
                        "//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[4]/div/div[2]/div/span"
                ));
                Assert.assertTrue(
                        errorMessage.isDisplayed(),
                        "Error message is not displayed for invalid Address Street 2 input: " + invalidInput
                );
                System.out.println("Invalid data test passed for input: " + invalidInput);
            } else {
                System.out.println("Test Failed: Error message NOT displayed for invalid input: " + invalidInput);
                Assert.fail("Error message not displayed for invalid input: " + invalidInput);
            }
        }

        // Optional: click Save after testing the invalid inputs
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div/form/div[7]/button")));
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
