package com.cegeka.academy.qa.pageobjects;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
@Component
public class LoginPage extends BasePage {
    @FindBy(id = "userName")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login")
    private WebElement loginButton;
    @FindBy(id="name")
    private WebElement messageInvalid;
    @FindBy(id = "newUser")
    private WebElement newUserButton;

    private static boolean loggedIn = false;

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public BooksPage clickLoginButton() {
        FluentWait<WebDriver> wait = getFluentWait();
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        return new BooksPage();
    }

    public boolean isLoginPageDisplayed() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.main-header"), "Login"));
        } catch (TimeoutException ex) {
            return false;
        }
    }
    public boolean isMessageInvalidDisplayed() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
             return wait.until(ExpectedConditions.visibilityOf(messageInvalid)).isDisplayed();

        } catch (TimeoutException ex) {
            return false;
        }
    }
    public String getMessage()
    {
        return messageInvalid.getText();
    }

    public void clickNewUserButton()
    {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            WebElement newUser = wait.until(ExpectedConditions.visibilityOf(newUserButton));
            newUser.click();
        } catch (TimeoutException ex) {
            System.out.println("Timed out waiting for New User button to be visible");
        }
    }

}
