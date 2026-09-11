package com.ecommerce.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test='error']");

    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public InventoryPage login(String user, String pass) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(username))
            .sendKeys(user);

        wait.until(ExpectedConditions.visibilityOfElementLocated(password))
            .sendKeys(pass);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton))
            .click();

        return new InventoryPage(driver);
    }

    public String getLoginErrorMessage() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }
}