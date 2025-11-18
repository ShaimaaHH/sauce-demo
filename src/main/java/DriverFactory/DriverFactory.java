package DriverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

import static Utilities.DataUtils.getPropertyValue;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void driverSetup() {

        String browser = getPropertyValue("config", "Browser");
        if (browser == null) browser = "chrome";

        switch (browser.toLowerCase()) {

            case "edge": {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driverThreadLocal.set(new EdgeDriver(options));
                break;
            }
            default:
                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("--incognito");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-notifications");
                options.addArguments("--start-maximized");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driverThreadLocal.set(new ChromeDriver(options));
                break;
        }

    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();

    }

    public static void quitDriver() {
        getDriver().quit();
        driverThreadLocal.remove();
    }
}