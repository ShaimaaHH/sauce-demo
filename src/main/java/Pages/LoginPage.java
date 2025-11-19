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
        log.info("Logging out from the application");
        Utility.click(driver, menuButton);
        Utility.click(driver, logoutLink);
    }

    // validations
    @Step("Get error message text")
    public String getErrorMessage() {
        return Utility.getText(driver, errorMessage);
    }

    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        return Utility.isDisplayed(driver, errorMessage);
    }

    @Step("Verify password field masking")
    public boolean isPasswordMasked() {
        String type = Utility.getAttribute(driver, passwordField, "type");
        boolean isMasked = type.equals("password");
        log.info("Password field type attribute: {}, is masked: {}", type, isMasked);
        return isMasked;
    }

    // validate login page UI elements
    @Step("Verify login page logo visibility")
    public boolean isLoginLogoVisible() {
        boolean isVisible = Utility.isDisplayed(driver, loginLogo);
        log.info("Login page logo visibility: {}", isVisible);
        return isVisible;
    }

    @Step("Get username placeholder text")
    public String getUsernamePlaceholder() {
        String placeholder = Utility.getAttribute(driver, usernameField, "placeholder");
        log.info("username placeholder text: {}", placeholder);
        return placeholder;
    }

    @Step("Get password placeholder text")
    public String getPasswordPlaceholder() {
        String placeholder = Utility.getAttribute(driver, passwordField, "placeholder");
        log.info("password placeholder text: {}", placeholder);
        return placeholder;
    }
}