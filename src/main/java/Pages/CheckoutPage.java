package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;
    private final By firstName= By.id("first-name");
    private final By lastName= By.id("last-name");
    private final By zipCode= By.id("postal-code");
    private final By continueButton= By.id("continue");
    private final By finishButton= By.id("finish");
    private final By successMsg=By.className("complete-header");
    public CheckoutPage(WebDriver driver){
        this.driver=driver;
    }
    public void clickFinish(){
        driver.findElement(finishButton).click();
    }
    public boolean isOrderSuccess(){
        return driver.findElement(successMsg).getText().equals("Thank you for your order!");
    }
    public void fillInformation(String fn, String ln, String zip){
        driver.findElement(firstName).sendKeys(fn);
        driver.findElement(lastName).sendKeys(ln);
        driver.findElement(zipCode).sendKeys(zip);
        driver.findElement(continueButton).click();
    }
}
