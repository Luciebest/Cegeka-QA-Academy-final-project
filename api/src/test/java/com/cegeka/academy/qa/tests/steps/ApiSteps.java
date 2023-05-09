package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.backpack.TestBackpack;
import com.cegeka.academy.qa.configurations.FrameworkConfiguration;
import com.cegeka.academy.qa.json.model.Book;
import com.cegeka.academy.qa.json.model.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

@CucumberContextConfiguration
@ContextConfiguration(classes = FrameworkConfiguration.class)
public class ApiSteps {

    private User requestBody;
    private String endpointUrl;
    private Response response;
    private RequestSpecification request;
    private String oldIsbn;

    @Autowired
    private TestBackpack testBackpack;

    @Given("The API endpoint for creating a user is {string}")
    public void theAPIEndpointForCreatingAUserIs(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }
    @And("A request body with username {string} and password {string} is defined")
    public void aRequestBodyWithUsernameAndPasswordIsDefined(String username, String password) {
        requestBody = new User(username, password);
        testBackpack.setPassword(password);
        testBackpack.setUserName(username);
    }
    private boolean userAlreadyExists;
    @When("Check if the user with username {string} exists already")
    public void theUserWithUsernameDoesNotExist(String username) {
        response = given()
                .contentType("application/json")
                .when()
                .get(endpointUrl + "?username=" + username); // modify the URL to include the query parameter "username"
        if (response.getStatusCode() == 200) {
            // user already exists, skip POST request
            userAlreadyExists = true;
            return;
        }
        // initialize request for POST request
        request = given();
    }


    @When("The request is sent with method {string}")
    public void theRequestIsSentWithMethod(String httpMethod) {
        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .request(httpMethod, endpointUrl);
    }

    @Then("The response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }
    @Then("The response status code should be {int} if the user has just been created, or {int} if the user already existed")
    public void theResponseStatusCodeShouldBeIfTheUserHasJustBeenCreatedOrIfTheUserAlreadyExisted(int status201, int status200) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode == status201) {
            Assert.assertEquals(status201, actualStatusCode);
        } else if (actualStatusCode == status200) {
            Assert.assertEquals(status200, actualStatusCode);
        } else {
            Assert.fail("Unexpected status code: " + actualStatusCode);
        }
    }
    @Then("The response body should contain {string}, {string}, a list of {string} fields")
    public void theResponseBodyShouldContainUserIdUsernameAndAListOfBooksFields(String userIdField, String usernameField, String booksField) {
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        Assert.assertTrue(responseBody.has(userIdField));
        Assert.assertTrue(responseBody.has(usernameField));
        Assert.assertTrue(responseBody.has(booksField));
        testBackpack.setUserId(responseBody.getString(userIdField));
    }

    @Given("The API endpoint for generating a token is {string}")
    public void theAPIEndpointForGeneratingATokenIs(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }


    @When("The request for generating the token is sent with method {string}")
    public void theRequestForGeneratingTheTokenIsSentWithMethod(String httpMethod) {
        System.out.println(testBackpack.getPassword());
        System.out.println(testBackpack.getUserName());
        User requestBody = new User(testBackpack.getUserName(), testBackpack.getPassword());
        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .request(httpMethod, endpointUrl);
    }
    @Then("The response body should contain {string}, {string}, {string}, and {string} fields")
    public void theResponseBodyShouldContainTokenExpiresStatusAndResultFields(String tokenField, String expiresField, String statusField, String resultField) {
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        Assert.assertTrue(responseBody.has(tokenField));
        Assert.assertTrue(responseBody.has(expiresField));
        Assert.assertTrue(responseBody.has(statusField));
        Assert.assertTrue(responseBody.has(resultField));
        testBackpack.setToken(responseBody.getString(tokenField));

    }

    @Given("The API endpoint for getting a user is {string}")
    public void theAPIEndpointForGettingAUserIs(String endpoint) {
        this.endpointUrl = endpoint;
    }

    @And("I send a GET request to the user endpoint with UUID and I have a header called Authorization which contains the token")
    public void iHaveAHeaderCalledAuthorizationWithValueFromPreviousResponse() {
        RequestSpecification request = given();
        request.header("Authorization", "Bearer " + testBackpack.getToken());
        response = request.get(endpointUrl.replace("{UUID}", testBackpack.getUserId()));

    }

    @Given("The API endpoint for getting books is {string}")
    public void theAPIEndpointForGettingBooksIs(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }
    @When("I send a GET request to the books endpoint")
    public void iSendAGETRequestToTheBooksEndpoint() {
        response = given()
                .contentType("application/json")
                .when()
                .get(endpointUrl);
    }
    @Then("The response body should contain a list of books")
    public void theResponseBodyShouldContainListOfBooks() {
       String responseBody = response.getBody().asString();
        String bodyWithoutBrackets = responseBody.substring(responseBody.indexOf("[") + 1, responseBody.lastIndexOf("]"));
        String[] books = bodyWithoutBrackets.split("\\},\\{"); // split the string into an array of books
        List<String> isbns = new ArrayList<>();

        // loop through each book and extract the ISBN using regular expressions
        for (String book : books) {
            Pattern pattern = Pattern.compile("\"isbn\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(book);
            if (matcher.find()) {
                isbns.add(matcher.group(1));
            }
        }
        testBackpack.setIsbns(isbns);
        System.out.println(isbns);

    }

    @Given("The API endpoint for adding books to a user's profile is {string}")
    public void theAPIEndpointForAddingBooksToAUserSProfileIs(String endpointUrl) {
        this.endpointUrl = endpointUrl;

    }
    @When("The user chooses a random book by its isbn")
    public void theUserChoosesARandomBookByItsIsbn() {

        List<String> isbns = new ArrayList<>(testBackpack.getIsbns());
        String randomIsbn = isbns.get(new Random().nextInt(isbns.size()));
        Book book = new Book(randomIsbn);
        testBackpack.setBook(book);
        System.out.println(randomIsbn);
    }
    @And("I send a POST request and I have a header called Authorization which contains the token")
    public void iSendAPOSTRequestAndIHaveAHeaderCalledAuthorizationWhichContainsTheToken() {
        Book book = testBackpack.getBook();
        String userId = testBackpack.getUserId();

        //RestAssured class is used to send HTTP requests and receive responses
        RequestSpecification request = given();
        request.header("Authorization", "Bearer " + testBackpack.getToken());

        JSONObject requestParams = new JSONObject();
        requestParams.put("userId", userId);

        JSONArray collectionOfIsbns = new JSONArray();

        //JSONObject class is used to parse JSON response bodies
        JSONObject isbn = new JSONObject();
        isbn.put("isbn", book.getIsbn());
        collectionOfIsbns.put(isbn);

        requestParams.put("collectionOfIsbns", collectionOfIsbns);

        request.body(requestParams.toString());

        System.out.println(request);

        response = given()
                .header("Authorization", "Bearer " + testBackpack.getToken())
                .contentType("application/json")
                .body(requestParams.toString())
                .post(endpointUrl);

    }
    @Given("The API endpoint for updating a book is {string}")
    public void theAPIEndpointForUpdatingABookIs(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    @When("Another book is chosen randomly by its isbn")
    public void anotherBookIsChosenRandomlyByItsIsbn() {
        //RequestSpecification
        request = given();
        request.header("Authorization", "Bearer " + testBackpack.getToken());
        oldIsbn = testBackpack.getBook().getIsbn();
        request.get(endpointUrl.replace("{ISBN}", testBackpack.getBook().getIsbn()));


        List<String> isbns = new ArrayList<>(testBackpack.getIsbns());
        isbns.remove(testBackpack.getBook().getIsbn()); // remove the ISBN of the current book from the list of available ISBNs
        String randomIsbn = isbns.get(new Random().nextInt(isbns.size())); // choose a random ISBN from the remaining list of available books
        Book book = new Book(randomIsbn);
        testBackpack.setBook(book);
        System.out.println(randomIsbn);
    }

    @When("The request for updating is sent with method PUT and I have a header called Authorization which contains the token")
    public void theRequestForUpdatingIsSentWithMethod() {
        Book book = testBackpack.getBook();
        String isbn= book.getIsbn();
        String id=testBackpack.getUserId();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userId", id);
        requestParams.put("isbn", isbn);


        request.body(requestParams.toString());

        response = given()
                .header("Authorization", "Bearer " + testBackpack.getToken())
                .contentType("application/json")
                .body(requestParams.toString())
                .put(endpointUrl.replace("{ISBN}",oldIsbn));


    }

    @Given("The API endpoint for deleting a book is {string}")
    public void theAPIEndpointForDeletingABookIs(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    @When("The request is sent with method DELETE and I have a header called Authorization which contains the token")
    public void theRequestIsSentWithMethodDELETEAndIHaveAHeaderCalledAuthorizationWhichContainsTheToken() {
        // Prepare request body
        Book book = testBackpack.getBook();
        String isbn = book.getIsbn();
        String userId = testBackpack.getUserId();
        JSONObject requestBody = new JSONObject();
        requestBody.put("isbn", isbn);
        requestBody.put("userId", userId);

        // Send DELETE request
        response = given()
                .header("Authorization", "Bearer " + testBackpack.getToken())
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .delete(endpointUrl);
    }

    @Given("The API endpoint for getting a book is {string}")
    public void theAPIEndpointForGettingABookIs(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    @And("I send a GET request and the isbn is added to the endpoint")
    public void iSendAGETRequestAndTheIsbnIsAddedToTheEndpoint() {

            List<String> isbns = new ArrayList<>(testBackpack.getIsbns());
            String randomIsbn = isbns.get(new Random().nextInt(isbns.size()));
            Book book = new Book(randomIsbn);
            //testBackpack.setBook(book);
            System.out.println(randomIsbn);
            // Append the ISBN as a query parameter to the endpoint URL
            endpointUrl = endpointUrl + "?ISBN=" + randomIsbn;
            response = given()
                .contentType("application/json")
                .get(endpointUrl);
        }
    }
