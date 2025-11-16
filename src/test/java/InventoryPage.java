import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

@Test
public class InventoryPage {

    WebDriver driver;
    WebDriverWait wait;


    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://www.saucedemo.com/inventory.html", "User was not redirected to Inventory page after valid login");
    }


    public void logoVisibility() {
        WebElement logo = driver.findElement(By.className("app_logo"));
        Assert.assertTrue(logo.isDisplayed(), "logo is not visible");
    }

    public void menuButtonVisibility() {
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        Assert.assertTrue(menuButton.isDisplayed(), "menu button is not visible");
    }


    public void sortItemsByNameAToZ() {
        Select sortDropdown = new Select(driver.findElement(By.className("product_sort_container")));
        sortDropdown.selectByValue("az");
    }

    public void sortItemsByNameZToA() {
    }

    public void sortItemsByPriceLowToHigh() {
    }

    public void sortItemsByPriceHighToLow() {
    }

    public void sortingOptionsVisibility() {

    }

//    public void openAboutPage() {
//        driver.findElement(By.id("react-burger-menu-btn")).click();
//        wait.until(ExpectedConditions.elementToBeClickable(By.id("about_sidebar_link"))).click();
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertEquals(currentUrl, "https://saucelabs.com/");
//
//    }


//    @AfterMethod
//    public void tearDown() {
//        driver.quit();
//    }
}
