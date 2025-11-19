package Tests;

import DriverFactory.DriverFactory;
import Models.User;
import Pages.LoginPage;
import Utilities.DataUtils;
import Utilities.Utility;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("sauceDemo automation Tests")
@Feature("Login Functionality Tests")
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private String baseUrl;
    private String inventoryUrl;
    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);

    @BeforeMethod(alwaysRun = true)
    @Step("Setup WebDriver and navigate to Login Page")
    public void setup() {
        log.info("Initializing WebDriver and navigating to Login Page");
        DriverFactory.driverSetup();
        driver = DriverFactory.getDriver();
        baseUrl = DataUtils.getPropertyValue("config", "Base_URL")
                .orElseThrow(() -> new RuntimeException("Base URL not found in config.properties"));
        inventoryUrl = DataUtils.getPropertyValue("config", "Inventory_URL")
                .orElseThrow(() -> new RuntimeException("Inventory_URL not found"));
        log.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    @Step("Quit WebDriver")
    public void tearDown() {
        log.info("Quitting WebDriver");
        DriverFactory.quitDriver();
    }

    //helper methods
    @Step("login with user type: {type}")
    private void loginWithUserType(String type) {
        User user = DataUtils.getUserByType(type);

        log.info("logging in as user type: {}", type);
        loginPage.login(
                user.getUsername(),
                user.getPassword());
    }

    @Step("Verify user is redirected to Inventory page")
    private void verifyInventoryPageRedirect() {
        log.info("Verifying redirect to inventory page : {}", inventoryUrl);
        Assert.assertTrue(Utility.verifyUrl(driver, inventoryUrl),
                "Models.User was not redirected to Inventory page after valid login");
    }

    @Step("Verify user is redirected to Login page after logout")
    private void verifyLoginPageRedirect() {
        Assert.assertTrue(Utility.verifyUrl(driver, baseUrl), "User was not redirected to Login page after logout");
    }

    @Step("verify error message is displayed")
    private void validateErrorMessage(@org.jetbrains.annotations.NotNull String expectedMessage) {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
        if (!expectedMessage.isEmpty()) {
            String actual = loginPage.getErrorMessage();
            log.info("Actual error message: {}", actual);
            Assert.assertEquals(actual, expectedMessage, "Error message mismatch");
        }
    }

    //positive login tests
    @Test(description = "Login with standard user")
    public void loginWithStandardUser() {
        loginWithUserType("standard");
        verifyInventoryPageRedirect();
    }

    @Test(description = "Login with problem user")
    public void loginWithProblemUser() {
        loginWithUserType("problem");
        verifyInventoryPageRedirect();
    }

    @Test(description = "Login with performance glitch user")
    public void loginWithPerformanceGlitchUser() {
        loginWithUserType("performance glitch");
        verifyInventoryPageRedirect();
    }

    @Test(description = "Login with visual user")
    public void loginWithVisualUser() {
        loginWithUserType("visual");
        verifyInventoryPageRedirect();
    }

    @Test(description = "Login with error user")
    public void loginWithErrorUser() {
        loginWithUserType("error");
        verifyInventoryPageRedirect();
    }

    //negative login tests
    @Test(description = "Login with locked out user")
    public void loginWithLockedOutUser() {
        loginWithUserType("locked out");
        validateErrorMessage("Epic sadface: Sorry, this user has been locked out.");
    }

    @Test(description = "Login with invalid username")
    public void loginWithInvalidUsername() {
        loginWithUserType("invalid username");
        validateErrorMessage("Epic sadface: Username and password do not match any user in this service");
    }

    @Test(description = "Login with invalid password")
    public void loginWithInvalidPassword() {
        loginWithUserType("invalid password");
        validateErrorMessage("Epic sadface: Username and password do not match any user in this service");
    }

    @Test(description = "Login with empty username")
    public void loginWithEmptyUsername() {
        loginWithUserType("empty username");
        validateErrorMessage("Epic sadface: Username is required");
    }

    @Test(description = "Login with empty password")
    public void loginWithEmptyPassword() {
        loginWithUserType("empty password");
        validateErrorMessage("Epic sadface: Password is required");
    }

    @Test(description = "Login with empty username and password")
    public void loginWithEmptyFields() {
        loginWithUserType("empty username and password");
        validateErrorMessage("Epic sadface: Username is required");
    }

    //UI tests
    @Test(description = "Password masking validation", groups = {"UI"})
    public void verifyPasswordMasking() {
        log.info("Validate password masking");
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password field is NOT masked!");
    }

    @Test(description = "Verify login page logo is visible", groups = {"UI"})
    public void verifyLoginPageLogoVisibility() {
        Assert.assertTrue(
                loginPage.isLoginLogoVisible(), "Login page logo is not visible");
    }

    @Test(description = "username placeholder validation", groups = {"UI"})
    public void verifyUsernamePlaceholder() {
        log.info("Validating username placeholder text");
        String actualPlaceholder = loginPage.getUsernamePlaceholder();
        Assert.assertEquals(actualPlaceholder, "Username", "Username placeholder text is incorrect!");
    }

    @Test(description = "Password placeholder validation", groups = {"UI"})
    public void verifyPasswordPlaceholder() {
        log.info("Validating password placeholder text");
        String actualPlaceholder = loginPage.getPasswordPlaceholder();
        Assert.assertEquals(actualPlaceholder, "Password", "Password placeholder text is incorrect!");
    }

    // logout test
    @Test(description = "Logout and redirect to login page")
    public void logoutTest() {
        loginWithUserType("standard");
        verifyInventoryPageRedirect();
        loginPage.logout();
        verifyLoginPageRedirect();
    }
}