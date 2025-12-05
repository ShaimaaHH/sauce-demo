package Tests;
import DriverFactory.DriverFactory;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Utilities.DataUtils;
import Utilities.LogUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest {
private WebDriver driver;
private LoginPage loginPage;
private InventoryPage inventoryPage;
private CartPage cartPage;
private String baseURL;
@BeforeMethod
    public void setup(){
    DriverFactory.driverSetup();
    driver=DriverFactory.getDriver();
    baseURL=DataUtils.getPropertyValue("config","Base_URL").orElseThrow();
    driver.get(baseURL);
    loginPage=new LoginPage(driver);
    inventoryPage=new InventoryPage(driver);
    cartPage=new CartPage(driver);
    loginPage.login("standard_user","secret_sauce");
}
@AfterMethod
    public void tearDown(){
    DriverFactory.quitDriver();
}
@Test
        public void verifyAddingItemsToList(){
    inventoryPage.addProductToCart("Sauce Labs Backpack");
inventoryPage.goToCart();
    Assert.assertTrue(cartPage.isPageLoaded(),"Cart page did not load!");
    Assert.assertEquals(cartPage.getCartItemsCount(),1);
}
@Test
    public void verifyRemovingItemsFromCart(){
    inventoryPage.addProductToCart("Sauce Labs Bolt T-Shirt");
            inventoryPage.goToCart();
    cartPage.removeFirstItem();
    Assert.assertEquals(cartPage.getCartItemsCount(),0);
}
@Test
public void verifyCheckoutButtonWorks(){
    inventoryPage.addProductToCart("Sauce Labs Onesie");
    inventoryPage.goToCart();
    cartPage.clickCheckout();
    Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
}


}
