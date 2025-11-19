package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryPage {

    private WebDriver driver;
    private static Logger log = LoggerFactory.getLogger(InventoryPage.class);

    // locators
    private final By inventoryContainer = By.id("inventory_container");
    private final By inventoryItems = By.className("inventory_item");
    private final By addToCartButtons = By.cssSelector("button.btn_inventory");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    // constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // actions
    
}
