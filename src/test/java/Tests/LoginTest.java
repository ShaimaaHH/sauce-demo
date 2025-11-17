package Tests;

import DriverFactory.DriverFactory;
import Pages.LoginPage;
import Utilities.DataUtils;
import Utilities.Utility;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    @Step("Setup WebDriver and navigate to Login Page")
    public void setup() {
        DriverFactory.driverSetup();
        driver = DriverFactory.getDriver();
        String url = DataUtils.getPropertyValue("config", "Base_URL");
        driver.get(url);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    @Step("Quit WebDriver")
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Step("login with user type: {userType}")
    private void loginWithUserType(String userType) {
        JsonObject user = DataUtils.getUserByType("users", userType);
        String username = user.get("username").getAsString();
        String password = user.get("password").getAsString();
        loginPage.login(username, password);
    }

    @Test(description = "Login with Standard User")
    public void loginWithStandardUser() {
        loginWithUserType("standard");
        String expectedUrl = DataUtils.getPropertyValue("config", "Inventory_URL");
        Assert.assertTrue(Utility.verifyUrlRedirection(driver, expectedUrl), "User was not redirected to Inventory page after valid login");
    }

    @Test(description = "Login with Problem User")
    public void loginWithProblemUser() {
        loginWithUserType("problem");
        String expectedUrl = DataUtils.getPropertyValue("config", "Inventory_URL");
        Assert.assertTrue(Utility.verifyUrlRedirection(driver, expectedUrl), "User was not redirected to Inventory page after valid login");
    }

    @Test(description = "Login with Performance Glitch User")
    public void loginWithPerformanceGlitchUser() {
        loginWithUserType("performance glitch");
        String expectedUrl = DataUtils.getPropertyValue("config", "Inventory_URL");
        Assert.assertTrue(Utility.verifyUrlRedirection(driver, expectedUrl), "User was not redirected to Inventory page after valid login");
    }

    @Test(description = "Login with Visual User")
    public void loginWithVisualUser() {
        loginWithUserType("visual");
        String expectedUrl = DataUtils.getPropertyValue("config", "Inventory_URL");
        Assert.assertTrue(Utility.verifyUrlRedirection(driver, expectedUrl), "User was not redirected to Inventory page after valid login");
    }

    @Test(description = "Login with Error User")
    public void loginWithErrorUser() {
        loginWithUserType("error");
        String expectedUrl = DataUtils.getPropertyValue("config", "Inventory_URL");
        Assert.assertTrue(Utility.verifyUrlRedirection(driver, expectedUrl), "User was not redirected to Inventory page after valid login");
    }

    @Test(description = "Login with Locked Out User")
    public void loginWithLockedOutUser() {
        loginWithUserType("locked out");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed for locked out user");
    }
//        String actualText = driver.findElement(By.tagName("h3")).getText();
//        Assert.assertEquals(actualText, "Epic sadface: Sorry, this user has been locked out.");

    @Test(description = "Login with Invalid Username")
    public void loginWithInvalidUsername() {
        loginWithUserType("invalid username");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed for invalid username");

    }
//        String actualText = driver.findElement(By.tagName("h3")).getText();
//        Assert.assertEquals(actualText, "Epic sadface: Username and password do not match any user in this service");

    @Test(description = "Login with Invalid Password")
    public void loginWithInvalidPassword() {
        loginWithUserType("invalid password");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed for invalid password");
    }
//        String actualText = driver.findElement(By.tagName("h3")).getText();
//        Assert.assertEquals(actualText, "Epic sadface: Username and password do not match any user in this service");

    @Test(description = "Login with Empty Username")
    public void loginWithEmptyUsername() {
        loginWithUserType("empty username");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed for invalid password");
    }
//        String actualText = driver.findElement(By.tagName("h3")).getText();
//        Assert.assertEquals(actualText, "Epic sadface: Username is required");

    @Test(description = "Login with Empty Password")
    public void loginWithEmptyPassword() {
        loginWithUserType("empty password");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed for invalid password");
    }
//        String actualText = driver.findElement(By.tagName("h3")).getText();
//        Assert.assertEquals(actualText, "Epic sadface: Password is required");

    @Test(description = "Login with Empty Username and Password")
    public void loginWithEmptyFields() {
        loginWithUserType("empty username and password");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed for invalid password");
    }

//        String actualText = driver.findElement(By.tagName("h3")).getText();
//        Assert.assertEquals(actualText, "Epic sadface: Username is required");
}