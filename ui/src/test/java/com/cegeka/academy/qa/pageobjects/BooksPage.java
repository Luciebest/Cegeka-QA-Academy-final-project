package com.cegeka.academy.qa.pageobjects;


import com.cegeka.academy.qa.tests.steps.BookItem;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class BooksPage extends BasePage {
    // We should target the parent elements of the title spans
    // This should select all the span elements with class mr-2 that are direct children
    // of a div element which is a direct child of an element with class rt-tr-group
    @FindBy(css = "div[role='rowgroup'] span.mr-2")
    private WebElement books;
    @FindBy(id = "submit")
    private WebElement logoutButton;
    //  @FindBy(id = "item-3")
    @FindBy(css = ".element-group:last-of-type li:nth-child(3)")
    private WebElement profileButton;
    @FindBy(css = ".element-group:last-of-type li:nth-child(4)")
    private WebElement bookStoreAPIButton;
    @FindBy(id="see-book-Understanding ECMAScript 6")
    private WebElement lastBookOnPage;

    @FindBy(css=".rt-tr.-padRow.-odd")
    private WebElement missingBook;

    public boolean isBooksContainerDisplayed() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            return wait.until(ExpectedConditions.visibilityOf(books)).isDisplayed();
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

    public boolean isAlertPresentFluentWait() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            System.out.println("Timeout is present" + e);
            return false;
        }
    }
    public Alert switchToAlert() {
        return driver.switchTo().alert();
    }
    private static void waitInMillis(long millis) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }

    }
    public void scroll()
    {
        By footer = By.tagName("footer");
        int footerHeight = driver.findElement(footer).getRect().getDimension().getHeight();
        new Actions(driver).moveToElement(missingBook).perform();
        try {
            getFluentWait().until(ExpectedConditions.visibilityOf(missingBook));

        } catch (ElementClickInterceptedException ex) {
            new Actions(driver).scrollByAmount(0, footerHeight + 400).perform();
            waitInMillis(500);

            getFluentWait().until(ExpectedConditions.visibilityOf(missingBook));
        }
    }
    public void clickProfileButton() {

        By footer = By.tagName("footer");
        int footerHeight = driver.findElement(footer).getRect().getDimension().getHeight();
        new Actions(driver).moveToElement(profileButton).perform();
        try {
            getFluentWait().until(ExpectedConditions.visibilityOf(profileButton)).click();

        } catch (ElementClickInterceptedException ex) {
            new Actions(driver).scrollByAmount(0, footerHeight + 100).perform();
            waitInMillis(500);

            getFluentWait().until(ExpectedConditions.elementToBeClickable(profileButton)).click();
        }
    }

public List<BookItem> getBooks() {
    List<BookItem> result = new ArrayList<>();
    List<WebElement> bookWebElementList;
    FluentWait<WebDriver> wait = getFluentWait();
    try {
        bookWebElementList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".rt-tr-group")));
    } catch (TimeoutException ex) {
        return result;
    }
    for (WebElement bookWebElement : bookWebElementList) {
        String titleText = bookWebElement.findElement(By.cssSelector("div:nth-of-type(2)")).getText();
        String authorText = bookWebElement.findElement(By.cssSelector("div:nth-of-type(3)")).getText();
        String publisherText = bookWebElement.findElement(By.cssSelector("div:nth-of-type(4)")).getText();
        BookItem book = new BookItem();
        book.setTitle(titleText);
        book.setAuthor(authorText);
        book.setPublisher(publisherText);
        result.add(book);
    }
    return result;
}

    public void clickBook(String bookTitle) {
        WebElement bookElement = driver.findElement(By.id("see-book-" + bookTitle));
        Actions actions = new Actions(driver);
        actions.moveToElement(bookElement).click().perform();
    }
}