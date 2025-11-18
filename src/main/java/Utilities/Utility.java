package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utility {

    private static final int waitTime =
            Integer.parseInt(DataUtils.getPropertyValue("config", "waitTime"));

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }

    // wait methods
    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // action methods
    public static void click(WebDriver driver, By locator) {
        WebElement element = waitForClickability(driver, locator);
        element.click();
    }

    public static void sendKeys(WebDriver driver, By locator, String data) {
        WebElement element = waitForVisibility(driver, locator);
        element.clear();
        element.sendKeys(data);
    }

    public static String getText(WebDriver driver, By locator) {
        return waitForVisibility(driver, locator).getText();
    }

    // validation methods
    public static boolean verifyUrl(WebDriver driver, String expectedURL) {
        try {
            wait(driver).until(ExpectedConditions.urlToBe(expectedURL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDisplayed(WebDriver driver, By locator) {
        try {
            return waitForVisibility(driver, locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // web element method
    public static WebElement getElement(WebDriver driver, By locator) {
        return waitForPresence(driver, locator);
    }
}