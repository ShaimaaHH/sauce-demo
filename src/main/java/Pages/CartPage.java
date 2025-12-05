
package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

    public class CartPage {
        private WebDriver driver;
        //Locators
        private final By cartItems= By.className("cart_item");
        private final By removeButton= By.cssSelector(".cart_button");
        private final By title= By.className("title");
        private final By checkoutButton= By.id("checkout");
        public CartPage(WebDriver driver){
            this.driver= driver;
        }
        public int getCartItemsCount(){
            return driver.findElements(cartItems).size();
        }
        public boolean isPageLoaded(){
            return driver.findElement(title).getText().equals("Your Cart");
        }
        public void clickCheckout(){
             driver.findElement(checkoutButton).click();
        }
        public void removeFirstItem(){
            driver.findElements(removeButton).get(0).click();
        }


    }

