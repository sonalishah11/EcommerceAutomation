package com.ecommerce.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ecommerce.base.DriverFactory;
import com.ecommerce.pages.CartPage;
import com.ecommerce.pages.CheckoutPage;
import com.ecommerce.pages.InventoryPage;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.utilities.ConfigReader;
import com.ecommerce.utilities.ExcelReader;

public class LoginTest extends BaseTest {

    // ================= LOGIN TEST =================

    @Test(groups = "smoke")
    public void verifyLogin() {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // we entered wrong title intentionally because want to capture screenshot during failled the test case
        Assert.assertEquals(
                inventoryPage.getPageTitle(),
                "wrongtitle"
        );
    }


    // ================= DATA PROVIDER =================

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {

        return ExcelReader.getTestData();
    }


    // ================= DATA DRIVEN INVALID LOGIN =================

    @Test(
            dataProvider = "invalidLoginData",
            groups = "regression"
    )
    public void verifyInvalidLogin(String username, String password) {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                username,
                password
        );

        Assert.assertEquals(
                loginPage.getLoginErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service"
        );
    }


    // ================= EMPTY LOGIN =================

    @Test(groups = "regression")
    public void verifyLoginWithEmptyUsernamePassword() {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                "",
                ""
        );

        Assert.assertEquals(
                loginPage.getLoginErrorMessage(),
                "Epic sadface: Username is required"
        );
    }


    // ================= CART TESTS =================

    @Test(groups = "smoke")
    public void verifyAddProductToCart() {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        inventoryPage.addBackpackToCart();

        Assert.assertEquals(
                inventoryPage.getCartItemCount(),
                "1"
        );
    }


    @Test(groups = "regression")
    public void verifyProductInCart() {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        inventoryPage.addBackpackToCart();

        CartPage cartPage = inventoryPage.clickCart();

        Assert.assertEquals(
                cartPage.getProductName(),
                "Sauce Labs Backpack"
        );
    }


    @Test(groups = "regression")
    public void verifyRemoveProductFromCart() {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        inventoryPage.addBackpackToCart();

        CartPage cartPage = inventoryPage.clickCart();

        Assert.assertEquals(
                cartPage.getProductName(),
                "Sauce Labs Backpack"
        );

        cartPage.removeBackpackFromCart();

        Assert.assertEquals(
                driver.findElements(
                        By.className("inventory_item_name")
                ).size(),
                0
        );
    }


    // ================= CHECKOUT TESTS =================

    @Test(groups = "smoke")
    public void verifyCheckout() {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        inventoryPage.addBackpackToCart();

        CartPage cartPage = inventoryPage.clickCart();

        CheckoutPage checkoutPage = cartPage.clickCheckout();

        checkoutPage.enterCustomerDetails(
                "Sonali",
                "Sharma",
                "110001"
        );

        checkoutPage.clickContinue();

        Assert.assertEquals(
                checkoutPage.getPageTitle(),
                "Checkout: Overview"
        );
    }


    @Test(groups = "smoke")
    public void verifyOrderPlaced() {

        WebDriver driver = DriverFactory.getDriver();

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        inventoryPage.addBackpackToCart();

        CartPage cartPage = inventoryPage.clickCart();

        CheckoutPage checkoutPage = cartPage.clickCheckout();

        checkoutPage.enterCustomerDetails(
                "Sonali",
                "Sharma",
                "110001"
        );

        checkoutPage.clickContinue();

        Assert.assertEquals(
                checkoutPage.getPageTitle(),
                "Checkout: Overview"
        );

        checkoutPage.clickFinish();

        Assert.assertEquals(
                checkoutPage.getPageTitle(),
                "Checkout: Complete!"
        );
    }
}
