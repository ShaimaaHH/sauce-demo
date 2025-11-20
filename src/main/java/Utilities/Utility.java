package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Utility {

    private static final int waitTime =
            Integer.parseInt(DataUtils.getPropertyValue("config", "waitTime").orElse("10"));

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

    public static void waitForPageToLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
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

    public static boolean isProductDisplayed(WebDriver driver, By listLocator, String name, By nameLocator) {
        try {
            List<WebElement> items = getElements(driver, listLocator);
            for (WebElement item : items) {
                String productTitle = item.findElement(nameLocator).getText().trim();
                if (productTitle.equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    // webElement method
    public static WebElement getElement(WebDriver driver, By locator) {
        return waitForPresence(driver, locator);
    }

    public static List<WebElement> getElements(WebDriver driver, By locator) {
        try {
            return wait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        WebElement element = waitForPresence(driver, locator);
        return element.getAttribute(attribute);
    }

    public static WebElement findElementInList(List<WebElement> elements, Predicate<WebElement> condition) {
        for (WebElement e : elements) {
            if (condition.test(e)) return e;
        }
        return null;
    }


    public static void click(WebDriver driver, WebElement element) {
        element.click();
    }
}