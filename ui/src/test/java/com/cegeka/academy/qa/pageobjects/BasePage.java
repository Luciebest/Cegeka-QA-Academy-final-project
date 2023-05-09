package com.cegeka.academy.qa.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.time.Duration;

@Component
public class BasePage {
    @Value("${base.url:}")
    private String baseUrl;

    //   Field injection
    @Autowired
    protected WebDriver driver;

    @PostConstruct
    public void init() {
        System.out.println("Initializing page for class" + this.getClass());
        PageFactory.initElements(driver, this);
    }

    public void goToStartPage() {
        driver.get(baseUrl);
    }

    protected FluentWait<WebDriver> getFluentWait() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(8))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        return wait;
    }

}
