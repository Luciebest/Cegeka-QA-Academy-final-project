package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.database.dao.UserDao;
import com.cegeka.academy.qa.database.model.User;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.List;
import java.util.Random;


public class DemoQASteps extends BaseSteps {

    @Autowired
    @Qualifier("javascriptExecutor")
    protected JavascriptExecutor javascriptExecutor;
    @Autowired
    private BookItem bookItem;
    @Autowired
    private UserDao userDao;

    private User randomUser;

    @Given("Books page is opened")
    public void booksPageIsOpened() {
        booksPage.goToStartPage();
        booksPage.scroll();
        Assert.assertTrue(booksPage.isBooksContainerDisplayed());
    }
    @Then("Books page is opened and the user is logged in")
    public void booksPageIsOpenedAndUserIsLoggedIn() {
        Assert.assertTrue(booksPage.isBooksContainerDisplayed());
        Assert.assertTrue("User is not logged in", booksPage.isLogoutButtonDisplayed());
    }

    @When("The user clicks on the \"Login\" button")
    public void theUserClicksOnTheLoginButton() {
        loginPage.clickLoginButton();
    }
    @Then("Login page is opened")
    public void loginPageIsOpened() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @When("The user enters valid credentials {string} and {string}")
    public void theUserEntersValidCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("The login is attempted")
    public void theLoginIsAttempted() {
        loginPage.clickLoginButton();
    }
    @Then("Profile page is opened and the user is logged in")
    public void verifyProfilePage() {
        Assert.assertTrue("Profile page is not displayed", profilePage.isProfilePageDisplayed());
        Assert.assertTrue("User is not logged in", profilePage.isLogoutButtonDisplayed());
    }
    @Then("All the books are displayed on the page and we save all the displayed info about them")
    public void checkBookTitlesRefactored() {
        System.out.println(booksPage.getBooks());
    }
    @When("The user selects a book")
    public void theUserSelectsABook() {
        booksPage.scroll();

        // Get a list of books
        List<BookItem> books = booksPage.getBooks();

        // Randomly select a book from the list
        BookItem selectedBook = books.get(new Random().nextInt(books.size()));

        // Set the BookItem fields using the information from the selected book
        bookItem.setTitle(selectedBook.getTitle());
        bookItem.setAuthor(selectedBook.getAuthor());
        bookItem.setPublisher(selectedBook.getPublisher());

        // Click on the selected book
        booksPage.clickBook(selectedBook.getTitle());
    }

    @And("Details of that book are displayed")
    public void detailsOfBookDisplayed() {
        // Get the expected book details from the BookItem object set in the previous step
        String expectedTitle = bookItem.getTitle();
        String expectedAuthor = bookItem.getAuthor();
        String expectedPublisher = bookItem.getPublisher();

        String title = detailsPage.getTitle();
        String author = detailsPage.getAuthor();
        String publisher = detailsPage.getPublisher();

        // Save the new details too, we can use them later, maybe...
        String isbn = detailsPage.getISBN();
        bookItem.setIsbn(isbn);
        int totalPages = detailsPage.getTotalPages();
        bookItem.setTotalPages(totalPages);
        String description = detailsPage.getDescription();
        bookItem.setDescription(description);
        String website = detailsPage.getWebsite();
        bookItem.setWebsite(website);

        // Assert that the book details match the expected values
        Assert.assertEquals(expectedTitle, title);
        Assert.assertEquals(expectedAuthor, author);
        Assert.assertEquals(expectedPublisher, publisher);

    }

    @When("The user clicks on the \"Add To Your Collection\" button")
    public void theUserClicksOnAddToYourCollectionButton() {
        detailsPage.scroll();
        profilePage.clickAddToCollectionButton();
    }

    @Then("An alert with the message {string} pops up")
    public void anAlertWithTheMessagePopsUp(String expectedMessage) {
        Assert.assertTrue("Alert not present", booksPage.isAlertPresentFluentWait());
        Alert alert = booksPage.switchToAlert();
        String actualMessage = alert.getText().replace(".", "");
        alert.accept();
        Assert.assertEquals("Alert message is incorrect", expectedMessage, actualMessage);

    }

    @When("The user goes to the Profile Page")
    public void theUserGoesToTheProfilePage() {
        booksPage.clickProfileButton();
    }

    @Then("The added book is displayed in the Profile Page")
    public void theAddedBookIsDisplayedInTheProfilePage() {
        // Get the expected book details from the BookItem object set in the previous step
        String expectedTitle = bookItem.getTitle();
        String expectedAuthor = bookItem.getAuthor();
        String expectedPublisher = bookItem.getPublisher();

        // Get the book details from the ProfilePage object
        String actualTitle = profilePage.getBookTitle(expectedTitle);
        String actualAuthor = profilePage.getBookAuthor(expectedTitle);
        String actualPublisher = profilePage.getBookPublisher(expectedTitle);

        Assert.assertEquals(expectedTitle, actualTitle);
        Assert.assertEquals(expectedAuthor, actualAuthor);
        Assert.assertEquals(expectedPublisher, actualPublisher);
    }

    @When("The user selects the added book and clicks on the {string} icon")
    public void theUserSelectsTheAddedBookAndClicksOnTheIcon(String iconName) {
        String bookTitle = bookItem.getTitle();
        profilePage.clickDeleteBookIcon(bookTitle);
    }

    @Then("A modal with the message {string} pops up")
    public void aDifferentAlertWithTheMessagePopsUp(String expectedMessage) {
        String actualMessage = profilePage.getMessageText();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @And("The user clicks on the OK button")
    public void theUserClicksOnTheOkButton() {
        profilePage.clickOkButton();
    }
    @Then("The added book should be removed")
    public void theAddedBookShouldBeRemoved() {
       boolean isBookRemoved = profilePage.verifyBookIsRemoved(bookItem.getTitle());
        Assert.assertTrue("The added book is still present on the page", isBookRemoved); // assert that the book is removed
    }

    @Then("Details page is opened")
    public void detailsPageIsOpened() {
        Assert.assertTrue("Details page is not opened", detailsPage.isISBNDisplayed());
    }

    @When("The user clicks on the \"Delete Account\" button")
    public void theUserClicksOnTheDeleteAccountButton() {
        profilePage.clickDeleteAccountButton();
    }

    @Then("A text with an error message {string} is displayed on the page")
    public void aTextWithAnErrorMessageIsDisplayedOnThePage(String expectedMessage) {
        Assert.assertTrue("The message doesn't show up", loginPage.isMessageInvalidDisplayed());
        Assert.assertEquals("The messages are not the same",expectedMessage, loginPage.getMessage() );
    }

    @When("The user clicks on the \"New User\" button")
    public void theUserClicksOnTheNewUserButton() {
    loginPage.clickNewUserButton();
    }

    @Then("Register page is opened")
    public void registerPageIsOpened() {
        Assert.assertTrue(registerPage.isRegisterPageDisplayed());
    }

    @When("The user enters their {string}, {string}, {string} and {string}")
    public void theUserEntersTheirAnd(String firstname, String lastname, String username, String password) {
       registerPage.enterFirstname(firstname);
       registerPage.enterLastname(lastname);
       registerPage.enterUsername(username);
       registerPage.enterPassword(password);
    }

    @And("The user checks the \"I'm not a robot\" checkbox")
    public void theUserChecksTheCheckbox() {
        registerPage.checkRecaptchaCheckbox();
    }

    @And("The user clicks on the \"Register\" button")
    public void theUserClicksOnTheRegisterButton() {
        registerPage.clickRegisterButton();
    }

    @When("The user goes to the Login Page")
    public void theUserGoesToTheLoginPage() {
       registerPage.scroll();
        registerPage.clickLoginButton();

    }

    @Then("A new alert with the message {string} pops up")
    public void anAlertPopsUp(String expectedMessage) {
        Assert.assertTrue("Alert not present", registerPage.isAlertPresentFluentWait());
        Alert alert = registerPage.switchToAlert();
        alert.accept();
    }

    @Given("All users are retrieved from db and I save one of them randomly")
    public void allUsersAreRetrievedFromDb() {
        List<User> allUsers=userDao.getAllUsers();
        System.out.println(allUsers);

        // Get a random user from the list
        Random random = new Random();
        int randomIndex = random.nextInt(allUsers.size());
        randomUser = allUsers.get(randomIndex);

        // Extract the username and password from the printed string
        String userString = allUsers.get(randomIndex).toString();
        String[] userParts = userString.split("userName='|', password='");
        String randomUserName = userParts[1];
        String randomPassword = userParts[2].replace("'}", "");

        System.out.println(randomUserName);
        System.out.println(randomPassword);

        randomUser.setUserName(randomUserName);
        randomUser.setPassword(randomPassword);
    }

    @Then("We enter valid credentials saved from the database")
    public void weEnterValidCredentialsSavedFromTheDatabase() {
        String username=randomUser.getUserName();
        String password=randomUser.getPassword();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }
}
