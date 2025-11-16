import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class LoginPageFeatures {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public void verifyPasswordMasking() {
        String type = driver.findElement(By.id("password")).getAttribute("type");
        Assert.assertEquals(type, "password");
    }

    public void loginPageLogoVisibility() {
        WebElement logo = driver.findElement(By.className("login_logo"));
        Assert.assertTrue(logo.isDisplayed(), "logo is not visible");
    }

    public void usernamePlaceholderTextVisibility() {
        String usernamePlaceholder = driver.findElement(By.id("user-name")).getAttribute("placeholder");
        Assert.assertEquals(usernamePlaceholder, ("Username"), "placeholder is not visible");
    }

    public void passwordPlaceholderTextVisibility() {
        String passwordPlaceholder = driver.findElement(By.id("password")).getAttribute("placeholder");
        Assert.assertEquals(passwordPlaceholder, ("Password"), "placeholder is not visible");
    }
}
