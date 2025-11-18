package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utility {

    private static final int waitTime = Integer.parseInt(DataUtils.getPropertyValue("config", "waitTime"));

    public static WebDriverWait general(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }

    public static void clickOnElement(WebDriver driver, By locator) {
        general(driver).until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    public static void sendData(WebDriver driver, By locator, String data) {
        general(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(data);
    }

    public static boolean verifyUrlRedirection(WebDriver driver, String expectedURL) {
        try {
            Utility.general(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isErrorMessageDisplayed(WebDriver driver, By locator) {
        try {
            general(driver)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static String getTextData(WebDriver driver, By locator) {
        general(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            general(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static WebElement toWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

}