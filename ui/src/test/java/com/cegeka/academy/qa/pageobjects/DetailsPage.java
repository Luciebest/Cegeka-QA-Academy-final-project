package com.cegeka.academy.qa.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

@Component
public class DetailsPage extends BasePage {
    @FindBy(css = "#ISBN-wrapper :nth-child(2)")
    private WebElement isbnElement;
    @FindBy(css = "#title-wrapper :nth-child(2)")
    private WebElement titleElement;

    @FindBy(css = "#author-wrapper :nth-child(2)")
    private WebElement authorElement;

    @FindBy(css = "#publisher-wrapper :nth-child(2)")
    private WebElement publisherElement;
    @FindBy(css = "#pages-wrapper :nth-child(2)")
    private WebElement pagesElement;

    @FindBy(css = "#description-wrapper :nth-child(2)")
    private WebElement descriptionElement;

    @FindBy(css = "#website-wrapper :nth-child(2)")
    private WebElement websiteElement;
    @FindBy(css = ".element-group:last-of-type li:nth-child(4)")
    private WebElement ApiButton;

    public boolean isISBNDisplayed() {
        FluentWait<WebDriver> wait = getFluentWait();
        try {
            return wait.until(ExpectedConditions.visibilityOf(isbnElement)).isDisplayed();
        } catch (TimeoutException ex) {
            return false;
        }
    }
    public String getISBN() {
        return isbnElement.getText();
    }
    public String getTitle() {
        return titleElement.getText();
    }

    public String getAuthor() {
        return authorElement.getText();
    }

    public String getPublisher() {
        return publisherElement.getText();
    }
    public int getTotalPages() {
        String totalPagesText = pagesElement.getText();
        return Integer.parseInt(totalPagesText);
    }

    public String getDescription() {
        return descriptionElement.getText();
    }

    public String getWebsite() {
        return websiteElement.getText();
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
        new Actions(driver).moveToElement(ApiButton).perform();
        try {
            getFluentWait().until(ExpectedConditions.visibilityOf(ApiButton));

        } catch (ElementClickInterceptedException ex) {
            new Actions(driver).scrollByAmount(0, footerHeight + 400).perform();
            waitInMillis(500);

            getFluentWait().until(ExpectedConditions.visibilityOf(ApiButton));
        }
    }

}
