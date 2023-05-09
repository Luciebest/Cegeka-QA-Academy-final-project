package com.cegeka.academy.qa.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RegisterPage extends BasePage {
    @FindBy(css = "div.main-header")
    private WebElement registerHeader;
    @FindBy(id = "firstname")
    private WebElement firstnameField;
    @FindBy(id = "lastname")
    private WebElement lastnameField;
    @FindBy(id = "userName")
    private WebElement usernameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(css = "span.recaptcha-checkbox[aria-checked='false']")
    private WebElement recaptchaCheckbox;
    @FindBy(id="register")
    private WebElement registerButton;
    @FindBy(css = ".element-group:last-of-type li:nth-child(1)")
    private WebElement loginButton;
    @FindBy(css = ".element-group:last-of-type li:nth-child(4)")
    private WebElement ApiButton;

    public boolean isRegisterPageDisplayed() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            return wait.until(ExpectedConditions.textToBePresentInElement(registerHeader, "Register"));
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void checkRecaptchaCheckbox() {
        //  Add a delay of 30 seconds before proceeding
        try {
            TimeUnit.SECONDS.sleep(30);
            }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }
    public void enterFirstname(String firstname) {
        firstnameField.sendKeys(firstname);
    }

    public void enterLastname(String lastname) {
        lastnameField.sendKeys(lastname);
    }
    public void clickRegisterButton() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            WebElement registerBtn = wait.until(ExpectedConditions.visibilityOf(registerButton));
            registerBtn.click();
        } catch (TimeoutException ex) {
            System.out.println("Timed out waiting for OK button to be visible");
        }
    }
    public void clickLoginButton() {
        loginButton.click();

    }
    public boolean isAlertPresentFluentWait() {

        FluentWait<WebDriver> wait = getFluentWait();
        try {
            wait.until(driver -> {
                try {
                    Alert alert = driver.switchTo().alert();
                    return true;
                } catch (NoAlertPresentException e) {
                    return false;
                }
            });
            return true;
        } catch (TimeoutException e) {
            System.out.println("Timeout is present: " + e);
            return true;
        } catch (AssertionError e) {
            System.out.println("Alert not present: " + e);
            return true;
        }
    }
    public Alert switchToAlert() {
        return driver.switchTo().alert();
    }
    public void scroll()
    {
        By footer = By.tagName("footer");
        int footerHeight = driver.findElement(footer).getRect().getDimension().getHeight();
        new Actions(driver).moveToElement(ApiButton).perform();
        try {
            getFluentWait().until(ExpectedConditions.visibilityOf(ApiButton));

        } catch (ElementClickInterceptedException ex) {
            new Actions(driver).scrollByAmount(0,  footerHeight+400).perform();
            waitInMillis(500);

            getFluentWait().until(ExpectedConditions.visibilityOf(ApiButton));
        }
    }
    private static void waitInMillis(long millis) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }

    }

}
