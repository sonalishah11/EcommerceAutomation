package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");

    private By continueButton = By.id("continue");
    private By finishButton = By.id("finish");

    private By pageTitle = By.className("title");

    private By successMessage = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void enterCustomerDetails(String first, String last, String postal) {
        type(firstName, first);
        type(lastName, last);
        type(postalCode, postal);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public void clickFinish() {
        click(finishButton);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }
}