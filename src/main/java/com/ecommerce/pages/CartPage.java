package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private By backpackItem = By.className("inventory_item_name");
    private By removeBackpackButton =
            By.id("remove-sauce-labs-backpack");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(backpackItem);
    }

    public void removeBackpackFromCart() {
        click(removeBackpackButton);
    }
    public CheckoutPage clickCheckout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }
}