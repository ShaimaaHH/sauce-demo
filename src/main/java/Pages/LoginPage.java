package Pages;

import Utilities.Utility;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Enter username: {0}")
    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        Utility.sendKeys(driver, usernameField, username);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        log.info("Entering password");
        Utility.sendKeys(driver, passwordField, password);
    }

    @Step("Click on Login button")
    public void clickLogin() {
        log.info("Clicking on Login button");
        Utility.click(driver, loginButton);
    }


    @Step("Perform login with username: {username}")
    public void login(String username, String password) {
        log.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    @Step("Get error message text")
    public String getErrorMessage() {
        log.info("Getting error message text");
        return Utility.getText(driver, errorMessage);
    }

    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        log.info("Checking if error message is displayed");
        return Utility.isDisplayed(driver, errorMessage);
    }
}