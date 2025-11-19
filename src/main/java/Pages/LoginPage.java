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

    // locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By loginLogo = By.className("login_logo");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");


    // constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // actions
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

    @Step("Perform login with username: {0}")
    public void login(String username, String password) {
        log.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    @Step("Logout from the application")
    public void logout() {
        Utility.click(driver, menuButton);
        Utility.click(driver, logoutLink);
    }

    // validations
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

    @Step("Verify password field masking")
    public boolean isPasswordMasked() {
        log.info("Checking if password field is masked (type='password')");
        String type = Utility.getAttribute(driver, passwordField, "type");
        boolean isMasked = type.equals("password");
        log.info("password field type attribute: {}", type);
        log.info("Password field masking status: {}", isMasked);
        return isMasked;
    }

    // validate login page UI elements
    @Step("Verify login page logo visibility")
    public boolean isLoginLogoVisible() {
        log.info("Checking visibility of login page logo");
        return Utility.isDisplayed(driver, loginLogo);
    }

    @Step("Get username placeholder text")
    public String getUsernamePlaceholder() {
        log.info("Getting username placeholder");
        return Utility.getAttribute(driver, usernameField, "placeholder");
    }

    @Step("Get password placeholder text")
    public String getPasswordPlaceholder() {
        log.info("Getting password placeholder");
        return Utility.getAttribute(driver, passwordField, "placeholder");
    }
}