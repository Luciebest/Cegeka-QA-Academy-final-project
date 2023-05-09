package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.configurations.FrameworkConfiguration;
import com.cegeka.academy.qa.pageobjects.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = FrameworkConfiguration.class)
public class BaseSteps {

    @Autowired
    protected BooksPage booksPage;

    @Autowired
    protected LoginPage loginPage;

    @Autowired
    protected ProfilePage profilePage;

    @Autowired
    protected DetailsPage detailsPage;

    @Autowired
    protected RegisterPage registerPage;
}
