package com.cegeka.academy.qa.configurations;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import java.time.Duration;
import java.util.Random;



@Configuration
@ComponentScan(basePackages = "com.cegeka.academy.qa")
@PropertySource("classpath:framework.properties")
public class FrameworkConfiguration {
    @Bean
    @Qualifier("usersDataSource")
    public DataSource getUsersDataSource()
    {
        String url="jdbc:mysql://localhost:3306/user_db";
        String username="root";
        String password="root";
        return new DriverManagerDataSource(url, username, password);

    }
    @Bean
    @Qualifier("usersJdbcTemplate")
    public JdbcTemplate getUsersJdbcTemplate(@Autowired DataSource usersDataSource)
    {
        return new JdbcTemplate(usersDataSource);
    }

    private WebDriver driver;
    private final String browserType;

    @Value("${base.url:https://demoqa.com/books}")
    private String baseUrl;

    public FrameworkConfiguration(@Value("${browser:random}") String browserType) {
        this.browserType = browserType;
    }

    @Bean(name = "driver")
    WebDriver getDriver(@Value("${browser:random}") String browserType) {
        WebDriver driver;

        if (browserType.equalsIgnoreCase("random")) {
            driver = getRandomDriver();
        } else {
            driver = switch (browserType.toLowerCase()) {
                case "chrome" -> getChrome();
                case "edge" -> getEdge();
                default -> throw new RuntimeException("Browser type is not supported: " + browserType);
            };
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    @Bean(name="javascriptExecutor")
    JavascriptExecutor getJavascriptExecutor(WebDriver getDriver)
    {
        return ((JavascriptExecutor) getDriver);
    }
    private WebDriver getRandomDriver() {
        Random random = new Random();
        int randomNumber = random.nextInt(2); // Generates 0 or 1

        if (randomNumber == 0) {
            return getChrome();
        } else {
            return getEdge();
        }
    }
    private ChromeDriver getChrome() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lucia\\developer\\chromedrivers\\chromedriver112\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized"); //always in fullscreen
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");

        return  new ChromeDriver(chromeOptions);
    }

    private EdgeDriver getEdge() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\Lucia\\developer\\edgedrivers\\edgedriver113\\msedgedriver.exe");
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-maximized"); //always in fullscreen
        edgeOptions.addArguments("--remote-allow-origins=*");

        return new EdgeDriver(edgeOptions);
    }

    @PreDestroy
    public void destroy() {
        if (driver != null)  {
            driver.quit();
            System.out.println("Driver quit was called");
        }
        else System.out.println("Cannot quit driver because it's already null");
    }

}

