package Tests;

import DriverFactory.DriverFactory;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest {
private WebDriver driver;
private LoginPage loginPage;
private InventoryPage inventoryPage;
private CartPage cartPage;
private  CheckoutPage checkoutPage;
@BeforeMethod
public void setup(){
    DriverFactory.driverSetup();
    driver= DriverFactory.getDriver();
    driver.get("https://www.saucedemo.com/");
    loginPage = new LoginPage(driver);
    inventoryPage = new InventoryPage(driver);
    cartPage = new CartPage(driver);
    checkoutPage = new CheckoutPage(driver);
    loginPage.login("standard_user", "secret_sauce");
    inventoryPage.addProductToCart("Sauce Labs Bike Light");
    inventoryPage.goToCart();
    cartPage.clickCheckout();
}
@AfterMethod
    public void tearDown(){
    DriverFactory.quitDriver();
}
@Test
    public void verifyCheckoutWithValidData(){
    checkoutPage.fillInformation("Lougina", "Ehab","12345");
    checkoutPage.clickFinish();
    Assert.assertTrue(checkoutPage.isOrderSuccess(),"Order was not completed successfully!");
}
@Test
public void verifyMissingFirstName(){
    checkoutPage.fillInformation("","Ehab","12345");
    Assert.assertTrue(driver.getPageSource().contains("Error: First Name is required"));
}
@Test
    public void verifyZipAcceptsOnlyNumbers(){
    checkoutPage.fillInformation("Lougina","Ehab","abc");
    Assert.assertTrue(driver.getPageSource().contains("Error"));
}
}
