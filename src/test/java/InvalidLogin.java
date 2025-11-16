import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class InvalidLogin {

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

    public void loginWithLockedOutUser() {
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(actualText, "Epic sadface: Sorry, this user has been locked out.");
    }

    public void loginWithInvalidUsername() {
        driver.findElement(By.id("user-name")).sendKeys("Admin");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(actualText, "Epic sadface: Username and password do not match any user in this service");
    }

    public void loginWithInvalidPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("Password");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(actualText, "Epic sadface: Username and password do not match any user in this service");
    }

    public void loginWithEmptyUsername() {
        driver.findElement(By.id("user-name")).sendKeys("");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(actualText, "Epic sadface: Username is required");
    }

    public void loginWithEmptyPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(actualText, "Epic sadface: Password is required");
    }

    public void loginWithEmptyFields() {
        driver.findElement(By.id("user-name")).sendKeys("");
        driver.findElement(By.id("password")).sendKeys("");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(actualText, "Epic sadface: Username is required");
    }
}
