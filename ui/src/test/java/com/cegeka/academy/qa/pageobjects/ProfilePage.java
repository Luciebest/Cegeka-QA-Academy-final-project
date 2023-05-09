package com.cegeka.academy.qa.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfilePage extends BasePage{
    @FindBy(id = "submit")
    private WebElement logoutButton;
    @FindBy(id="closeSmallModal-ok")
    private WebElement okButton;
    @FindBy(css = ".modal-body")
    private WebElement messageElement;
    @FindBy(id = "see-book-")
    private WebElement prefixBook;
    @FindBy(id = "delete-record-undefined")
    private WebElement deleteBookIcon;
    @FindBy(css = "div.main-header")
    private WebElement profileHeader;
    @FindBy(css = ".fullButtonWrap .text-right")
    private WebElement addToCollectionButton;
    @FindBy(css = "div[role='row'] div[id^='see-book-']")
    private List<WebElement> bookTitles;
    @FindBy(xpath = "./div[@role='gridcell'][2]")
    private By titleElement;

    @FindBy(xpath = "./ancestor::div[@role='row']")
    private By bookRowElement;

    @FindBy(xpath = "./div[@role='gridcell'][3]")
    private By authorElement;

    @FindBy(xpath = "./div[@role='gridcell'][4]")
    private By publisherElement;
    @FindBy(css = ".text-center.button #submit")
    private WebElement deleteButton;

    public void clickAddToCollectionButton() {
        Actions actions = new Actions(driver);
        actions.moveToElement(addToCollectionButton).click().perform();
    }
    public boolean isProfilePageDisplayed() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            return wait.until(ExpectedConditions.textToBePresentInElement(profileHeader, "Profile"));
        } catch (TimeoutException ex) {
            return false;
        }
    }
    public boolean isLogoutButtonDisplayed() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            return wait.until(ExpectedConditions.visibilityOf(logoutButton)).isDisplayed();
        } catch (TimeoutException ex) {
            return false;
        }
    }
    public void clickOkButton()
    {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            WebElement ok = wait.until(ExpectedConditions.visibilityOf(okButton));
            ok.click();
        } catch (TimeoutException ex) {
            System.out.println("Timed out waiting for OK button to be visible");
        }
    }

    public String getMessageText() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            wait.until(ExpectedConditions.visibilityOf(messageElement));
            return messageElement.getText().trim();
        } catch (TimeoutException ex) {
            return "";
        }
    }

    public boolean verifyBookIsRemoved(String bookName) {
        By bookLocator = By.id("see-book-" + bookName);
        try {
            WebElement titleBook = driver.findElement(bookLocator);
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public WebElement getBookTitleElement(String title) {
     return  driver.findElement(By.id(prefixBook + title));
    }

    public void clickDeleteBookIcon(String bookTitle) {
        WebElement titleBook = driver.findElement(By.id("see-book-" + bookTitle));
        WebElement bookRow = titleBook.findElement(By.xpath("./ancestor::div[@role='row']"));
        WebElement deleteBookIcon = bookRow.findElement(By.id("delete-record-undefined"));
        deleteBookIcon.click();

    }
    public WebElement getBookRow(String bookTitle) {
        return driver.findElement(By.id("see-book-" + bookTitle))
                .findElement(By.xpath("./ancestor::div[@role='row']"));
    }
    public String getBookTitle(String bookTitle) {
        return getBookRow(bookTitle).findElement(By.xpath("./div[@role='gridcell'][2]")).getText();
    }

    public String getBookAuthor(String bookTitle) {
        return getBookRow(bookTitle).findElement(By.xpath("./div[@role='gridcell'][3]")).getText();
    }

    public String getBookPublisher(String bookTitle) {
        return getBookRow(bookTitle).findElement(By.xpath("./div[@role='gridcell'][4]")).getText();
    }
    public void clickDeleteAccountButton()
    {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            WebElement delete = wait.until(ExpectedConditions.visibilityOf(deleteButton));
            delete.click();
        } catch (TimeoutException ex) {
            System.out.println("Timed out waiting for Delete Account button to be visible");
        }
    }

}
