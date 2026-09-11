package com.ecommerce.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage extends BasePage {

    // मान लेते हैं कि आपका लोकेटर कुछ ऐसा है, इसे अपने अनुसार रहने दें
    private By addBackpackBtn = By.id("add-to-cart-sauce-labs-backpack"); 
    private By pageTitle = By.className("title");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartButton = By.className("shopping_cart_link");

    private WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public void addBackpackToCart() {
        // यहाँ इंतजार जोड़ें ताकि "add-to-cart" बटन लोड हो सके
        wait.until(ExpectedConditions.elementToBeClickable(addBackpackBtn)).click();
    }

    public String getCartItemCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public CartPage clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
        return new CartPage(driver);
    }
}
