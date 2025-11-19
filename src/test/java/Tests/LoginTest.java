package Tests;

import DriverFactory.DriverFactory;
import Models.User;
import Pages.LoginPage;
import Utilities.DataUtils;
import Utilities.Utility;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("user management")
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private String baseUrl;
    private String inventoryUrl;

    public static Logger log = LogManager.getLogger(LoginTest.class);

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
    @Step("quit WebDriver")
    public void tearDown() {
        log.info("Quitting WebDriver");
        DriverFactory.quitDriver();
    }

    //helper methods

    @Step("Login as user type: {type}")
    private void loginWithUserType(String type) {
        User user = DataUtils.getUserByType(type);

        log.info("logging in as user type: {}", type);
        loginPage.login(
                user.getUsername(),
                user.getPassword());
    }

    @Step("Verify redirect to inventory page")
    private void verifyInventoryPageRedirect() {
        log.info("Verifying redirect to inventory page : {}", inventoryUrl);
        Assert.assertTrue(Utility.verifyUrl(driver, inventoryUrl),
                "Models.User was not redirected to Inventory page after valid login");
    }

    @Step("Verify redirect to login page")
    private void verifyLoginPageRedirect() {
        Assert.assertTrue(Utility.verifyUrl(driver, baseUrl), "User was not redirected to Login page after logout");
    }

    @Step("Validate error message: {expectedMessage}")
    private void validateErrorMessage(@org.jetbrains.annotations.NotNull String expectedMessage) {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
        if (!expectedMessage.isEmpty()) {
            String actual = loginPage.getErrorMessage();
            log.info("Actual error message: {}", actual);
            Assert.assertEquals(actual, expectedMessage, "Error message mismatch");
        }
    }

    //positive login tests

    @Feature("Login functionality")
    @Story("valid login scenario")
    @Test()
    public void loginWithStandardUser() {
        loginWithUserType("standard");
        verifyInventoryPageRedirect();
    }

    @Feature("Login functionality")
    @Story("valid login scenario")
    @Test()
    public void loginWithProblemUser() {
        loginWithUserType("problem");
        verifyInventoryPageRedirect();
    }

    @Feature("Login functionality")
    @Story("valid login scenario")
    @Test()
    public void loginWithPerformanceGlitchUser() {
        loginWithUserType("performance glitch");
        verifyInventoryPageRedirect();
    }

    @Feature("Login functionality")
    @Story("valid login scenario")
    @Test()
    public void loginWithVisualUser() {
        loginWithUserType("visual");
        verifyInventoryPageRedirect();
    }

    @Feature("Login functionality")
    @Story("valid login scenario")
    @Test()
    public void loginWithErrorUser() {
        loginWithUserType("error");
        verifyInventoryPageRedirect();
    }

    //negative login tests

    @Feature("Login functionality")
    @Story("invalid login scenario")
    @Test()
    public void loginWithLockedOutUser() {
        loginWithUserType("locked out");
        validateErrorMessage("Epic sadface: Sorry, this user has been locked out.");
    }

    @Feature("Login functionality")
    @Story("invalid login scenario")
    @Test()
    public void loginWithInvalidUsername() {
        loginWithUserType("invalid username");
        validateErrorMessage("Epic sadface: Username and password do not match any user in this service");
    }

    @Feature("Login functionality")
    @Story("invalid login scenario")
    @Test()
    public void loginWithInvalidPassword() {
        loginWithUserType("invalid password");
        validateErrorMessage("Epic sadface: Username and password do not match any user in this service");
    }

    @Feature("Login functionality")
    @Story("invalid login scenario")
    @Test()
    public void loginWithEmptyUsername() {
        loginWithUserType("empty username");
        validateErrorMessage("Epic sadface: Username is required");
    }

    @Feature("Login functionality")
    @Story("invalid login scenario")
    @Test()
    public void loginWithEmptyPassword() {
        loginWithUserType("empty password");
        validateErrorMessage("Epic sadface: Password is required");
    }

    @Feature("Login functionality")
    @Story("invalid login scenario")
    @Test()
    public void loginWithEmptyFields() {
        loginWithUserType("empty username and password");
        validateErrorMessage("Epic sadface: Username is required");
    }

    //UI tests

    @Feature("Login page UI element")
    @Test(groups = {"UI"})
    public void verifyPasswordMasking() {
        log.info("Validate password masking");
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password field is NOT masked!");
    }

    @Feature("Login page UI element")
    @Test(groups = {"UI"})
    public void verifyLoginPageLogoVisibility() {
        Assert.assertTrue(
                loginPage.isLoginLogoVisible(), "Login page logo is not visible");
    }

    @Feature("Login page UI element")
    @Test(groups = {"UI"})
    public void verifyUsernamePlaceholder() {
        log.info("Validating username placeholder text");
        String actualPlaceholder = loginPage.getUsernamePlaceholder();
        Assert.assertEquals(actualPlaceholder, "Username", "Username placeholder text is incorrect!");
    }

    @Feature("Login page UI element")
    @Test(groups = {"UI"})
    public void verifyPasswordPlaceholder() {
        log.info("Validating password placeholder text");
        String actualPlaceholder = loginPage.getPasswordPlaceholder();
        Assert.assertEquals(actualPlaceholder, "Password", "Password placeholder text is incorrect!");
    }

    // logout test

    @Feature("Logout functionality")
    @Test()
    public void logoutTest() {
        loginWithUserType("standard");
        verifyInventoryPageRedirect();
        loginPage.logout();
        verifyLoginPageRedirect();
    }
}