package Pages;

import Utilities.Utility;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {

    private WebDriver driver;

    private static final Logger log = LoggerFactory.getLogger(InventoryPage.class);

    public static final String SORT_NAME_ASC = "Name (A to Z)";
    public static final String SORT_NAME_DESC = "Name (Z to A)";
    public static final String SORT_PRICE_ASC = "Price (low to high)";
    public static final String SORT_PRICE_DESC = "Price (high to low)";

    // locators

    private final By sortDropdown = By.className("product_sort_container");
    private final By inventoryItems = By.className("inventory_item");
    private final By productPrice = By.className("inventory_item_price");
    private final By addToCartButton = By.xpath(".//button[contains(text(),'Add to cart')]");
    private final By removeButton = By.xpath(".//button[contains(text(),'Remove')]");
    private final By cartBadge = By.className("shopping_cart_badge");

    private final By productNameLocator = By.className("inventory_item_name");
    private final By productDetailName = By.className("inventory_details_name");
    private final By productDetailPrice = By.className("inventory_details_price");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By resetAppStateLink = By.id("reset_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    // Footer links
    private final By twitterLink = By.cssSelector("a[href='https://twitter.com/saucelabs']");
    private final By facebookLink = By.cssSelector("a[href='https://www.facebook.com/saucelabs']");
    private final By linkedinLink = By.cssSelector("a[href='https://www.linkedin.com/company/sauce-labs/']");

    // constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // actions

    @Step("sorting products by: {sortOption}")
    public void sortProducts(String sortOption) {
        log.info("Sorting products by option: {}", sortOption);
        Utility.waitForVisibility(driver, sortDropdown);
        Select select = new Select(Utility.getElement(driver, sortDropdown));
        select.selectByVisibleText(sortOption);
    }

    @Step("getting selected sort option")
    public String getSelectedSortOption() {
        Select select = new Select(Utility.getElement(driver, sortDropdown));
        return select.getFirstSelectedOption().getText();
    }

    @Step("getting all product names")
    public List<String> getProductNames() {
        List<WebElement> elements = Utility.getElements(driver, productNameLocator);
        List<String> result = new ArrayList<>();
        for (WebElement e : elements) {
            result.add(e.getText().trim());
        }
        return result;
    }

    @Step("getting all product prices")
    public List<Double> getProductPrices() {
        List<WebElement> elements = Utility.getElements(driver, productPrice);
        List<Double> result = new ArrayList<>();
        for (WebElement e : elements) {
            String priceText = e.getText().replace("$", "").trim();
            result.add(Double.parseDouble(priceText));
        }
        return result;
    }

    public boolean isProductDisplayed(String productName) {
        return Utility.isProductDisplayed(driver, inventoryItems, productName, productNameLocator);
    }

    @Step("Check if product name is displayed on detail page")
    public boolean isProductNameDisplayed() {
        return Utility.isDisplayed(driver, productDetailName);
    }

    @Step("Check if product price is displayed on detail page")
    public boolean isProductPriceDisplayed() {
        return Utility.isDisplayed(driver, productDetailPrice);
    }

    @Step("Get price for product: {name}")
    public double getPriceForProduct(String name) {
        List<WebElement> items = Utility.getElements(driver, inventoryItems);
        WebElement target = Utility.findElementInList(items, item -> item.findElement(productNameLocator).getText().trim().equalsIgnoreCase(name));
        if (target != null) {
            String price = target.findElement(productPrice).getText().replace("$", "").trim();
            return Double.parseDouble(price);
        }
        throw new RuntimeException("Product not found: " + name);
    }

    @Step("Add product to cart by name: {name}")
    public void addProductToCart(String name) {
        log.info("Adding product to cart: {}", name);
        List<WebElement> items = Utility.getElements(driver, inventoryItems);
        WebElement targetItem = Utility.findElementInList(items, item ->
                item.findElement(productNameLocator).getText().trim().equalsIgnoreCase(name));
        if (targetItem != null) {
            WebElement addButton = targetItem.findElement(addToCartButton);
            Utility.click(driver, addButton);
        } else {
            log.warn("Product '{}' not found on the inventory page", name);
        }
    }

    @Step("Remove product from cart by name: {name}")
    public void removeProductFromCart(String name) {
        log.info("Removing product from cart: {}", name);
        List<WebElement> items = Utility.getElements(driver, inventoryItems);
        WebElement targetItem = Utility.findElementInList(items, item ->
                item.findElement(productNameLocator).getText().trim().equalsIgnoreCase(name));
        if (targetItem != null) {
            WebElement removeBtn = targetItem.findElement(removeButton);
            Utility.click(driver, removeBtn);
        } else {
            log.warn("Product'{}' not found on the inventory page", name);
        }
    }


    @Step("Navigate to product detail page : {name}")
    public void openProductDetailPage(String name) {
        log.info("Navigating to product detail page: {}", name);
        List<WebElement> items = Utility.getElements(driver, inventoryItems);
        WebElement target = Utility.findElementInList(items, item -> item.findElement(productNameLocator).getText().equalsIgnoreCase(name));
        if (target != null) {
            Utility.click(driver, target.findElement(productNameLocator));
            Utility.waitForVisibility(driver, productDetailName);
        } else {
            log.warn("Product'{}'not found on the inventory page", name);
        }
    }

    @Step("Open side menu")
    public void openMenu() {
        Utility.waitForPageToLoad(driver);
        Utility.waitForClickability(driver, menuButton);
        Utility.click(driver, menuButton);
    }

    @Step("Get number of items in cart")
    public int getCartCount() {
        try {
            if (Utility.isDisplayed(driver, cartBadge)) {
                String countText = Utility.getElement(driver, cartBadge).getText().trim();
                return Integer.parseInt(countText);
            } else {
                return 0;
            }
        } catch (Exception e) {
            log.warn("Failed to get cart count, returning 0", e);
            return 0;
        }
    }


    @Step("Reset app state (remove all items from cart, etc.)")
    public void resetAppState() {
        Utility.click(driver, resetAppStateLink);
    }

    @Step("Check if Twitter link is visible")
    public boolean isTwitterLinkVisible() {
        return Utility.isDisplayed(driver, twitterLink);
    }

    @Step("Check if Facebook link is visible")
    public boolean isFacebookLinkVisible() {
        return Utility.isDisplayed(driver, facebookLink);
    }

    @Step("Check if LinkedIn link is visible")
    public boolean isLinkedInLinkVisible() {
        return Utility.isDisplayed(driver, linkedinLink);
    }

    @Step("Click on Twitter link")
    public void clickTwitterLink() {
        Utility.click(driver, twitterLink);
    }

    @Step("Click on Facebook link")
    public void clickFacebookLink() {
        Utility.click(driver, facebookLink);
    }

    @Step("Click on LinkedIn link")
    public void clickLinkedInLink() {
        Utility.click(driver, linkedinLink);
    }

    @Step("Get Twitter link URL")
    public String getTwitterLinkURL() {
        return Utility.getAttribute(driver, twitterLink, "href");
    }

    @Step("Get Facebook link URL")
    public String getFacebookLinkURL() {
        return Utility.getAttribute(driver, facebookLink, "href");
    }

    @Step("Get LinkedIn link URL")
    public String getLinkedInLinkURL() {
        return Utility.getAttribute(driver, linkedinLink, "href");
    }

    @Step("Logout from the application")
    public void logout() {
        log.info("Logging out user");
        Utility.click(driver, menuButton);
        Utility.waitForVisibility(driver, logoutLink);
        Utility.click(driver, logoutLink);
        Utility.waitForPageToLoad(driver);
    }

}
