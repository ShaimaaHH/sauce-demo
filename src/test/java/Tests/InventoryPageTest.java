package Tests;

import DriverFactory.DriverFactory;
import Models.User;
import Pages.InventoryPage;
import Pages.LoginPage;
import Utilities.DataUtils;
import Utilities.LogUtils;
import Utilities.Utility;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Epic("inventory management")

public class InventoryPageTest {

    private WebDriver driver;
    private InventoryPage inventoryPage;
    private LoginPage loginPage;
    private String baseUrl;
    private String inventoryUrl;

    @BeforeMethod(alwaysRun = true)
    @Step("Setup WebDriver and navigate to Inventory Page")
    public void setup() {
        LogUtils.info("Initializing WebDriver and navigating to Inventory Page");
        DriverFactory.driverSetup();
        driver = DriverFactory.getDriver();
        baseUrl = DataUtils.getPropertyValue("config", "Base_URL")
                .orElseThrow(() -> new RuntimeException("Base URL not found in config.properties"));
        inventoryUrl = DataUtils.getPropertyValue("config", "Inventory_URL")
                .orElseThrow(() -> new RuntimeException("Inventory_URL not found"));
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        User user = DataUtils.getUserByType("standard");
        LogUtils.info("logging in as standard user", user.getUsername());
        loginPage.login(
                user.getUsername(),
                user.getPassword()
        );
        Assert.assertTrue(Utility.verifyUrl(driver, inventoryUrl), "User was not redirected to Inventory page after login");
        inventoryPage = new InventoryPage(driver);
        LogUtils.info("Navigated to Inventory Page");
    }

    @AfterMethod(alwaysRun = true)
    @Step("quit WebDriver")
    public void tearDown() {
        LogUtils.info("Quitting WebDriver");
        DriverFactory.quitDriver();
    }


    @DataProvider(name = "sortOptions")
    public Object[][] sortOptionsProvider() {
        return new Object[][]{
                {InventoryPage.SORT_NAME_ASC},
                {InventoryPage.SORT_NAME_DESC},
                {InventoryPage.SORT_PRICE_ASC},
                {InventoryPage.SORT_PRICE_DESC}
        };
    }

    @Step("Validating sorted order")
    private <T extends Comparable<T>> void validateSorted(List<T> actual, boolean ascending) {
        List<T> expected = new ArrayList<>(actual);
        if (ascending) Collections.sort(expected);
        else Collections.sort(expected, Collections.reverseOrder());
        Assert.assertEquals(actual, expected);
    }

    @Step("Validating product order for sort option: {sortOption}")
    private void validateProductOrder(String sortOption) {
        if (sortOption.contains("Name")) {
            validateSorted(inventoryPage.getProductNames(), sortOption.equals("Name (A to Z)"));
        } else {
            validateSorted(inventoryPage.getProductPrices(), sortOption.equals("Price (low to high)"));
        }
    }

    @Feature("Product Sorting")
    @Test(dataProvider = "sortOptions")
    public void sortProductsTest(String sortOption) {
        LogUtils.info("Testing sorting by: {}", sortOption);
        inventoryPage.sortProducts(sortOption);
        Assert.assertEquals(inventoryPage.getSelectedSortOption(), sortOption, "Dropdown selection mismatch!");
        validateProductOrder(sortOption);
        Utility.waitForPageToLoad(driver);
    }

    @Feature("add to cart")
    @Test
    public void addProductToCartTest() {
        String product = "Sauce Labs Backpack";
        LogUtils.info("Initial cart count: " + inventoryPage.getCartCount());
        inventoryPage.addProductToCart(product);
        int cartCount = inventoryPage.getCartCount();
        LogUtils.info("Cart count after adding product: " + cartCount);
        Assert.assertEquals(cartCount, 1, "Cart count should be 1 after adding product");
    }

    @Feature("remove from cart")
    @Test
    public void removeProductFromCartTest() {
        String product = "Sauce Labs Backpack";
        inventoryPage.addProductToCart(product);
        Assert.assertEquals(inventoryPage.getCartCount(), 1, "Cart count should be 1 after adding product");
        inventoryPage.removeProductFromCart(product);
        int cartCount = inventoryPage.getCartCount();
        LogUtils.info("Cart count after removing product: " + cartCount);
        Assert.assertEquals(inventoryPage.getCartCount(), 0, "Cart count should be 0 after removing product");
    }


    @Feature("navigate to product detail")
    @Test
    public void openProductDetailTest() {
        String product = "Sauce Labs Backpack";
        LogUtils.info("Navigating to product detail page for: ", product);
        inventoryPage.openProductDetailPage(product);
        String currentUrl = driver.getCurrentUrl();
        LogUtils.info("Current URL after navigation: ", currentUrl);
        Assert.assertTrue(currentUrl.contains("inventory-item.html"), "URL does not contain expected product detail path");
    }

    @Test
    public void verifyProductDetailsOnInventoryPage() {
        String product = "Sauce Labs Backpack";
        Assert.assertTrue(inventoryPage.isProductDisplayed(product));
        Assert.assertEquals(inventoryPage.getPriceForProduct(product), 29.99);
    }

    @Test
    public void productDetailContentTest() {
        String productName = "Sauce Labs Backpack";
        inventoryPage.openProductDetailPage(productName);
        Assert.assertTrue(inventoryPage.isProductNameDisplayed(), "Product name is not visible on detail page");
        Assert.assertTrue(inventoryPage.isProductPriceDisplayed(), "Product price is not visible on detail page");
    }

    @Test
    public void resetAppStateTest() {
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getCartCount(), 1);
        inventoryPage.openMenu();
        inventoryPage.resetAppState();
        Assert.assertEquals(inventoryPage.getCartCount(), 0);
    }

    @Test
    public void logoutTest() {
        inventoryPage.logout();
        Assert.assertTrue(Utility.verifyUrl(driver, baseUrl));
    }

    @Feature("footer links")
    @Test
    public void footerLinksTest() {
        Assert.assertTrue(inventoryPage.isTwitterLinkVisible(), "Twitter link is not visible");
        Assert.assertTrue(inventoryPage.isFacebookLinkVisible(), "Facebook link is not visible");
        Assert.assertTrue(inventoryPage.isLinkedInLinkVisible(), "LinkedIn link is not visible");
        Assert.assertEquals(inventoryPage.getTwitterLinkURL(), "https://twitter.com/saucelabs");
        Assert.assertEquals(inventoryPage.getFacebookLinkURL(), "https://www.facebook.com/saucelabs");
        Assert.assertEquals(inventoryPage.getLinkedInLinkURL(), "https://www.linkedin.com/company/sauce-labs/");
    }
}

