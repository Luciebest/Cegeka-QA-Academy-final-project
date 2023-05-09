Feature: Api Testing
# I recommend running all scenarios at a time

    Scenario Outline: Create User using POST
        Given The API endpoint for creating a user is "https://demoqa.com/Account/v1/User"

        And A request body with username "<userName>" and password "<password>" is defined
        When The request is sent with method "POST"
        Then The response status code should be 201
        And The response body should contain "userID", "username", a list of "books" fields

      #!!!!
      #Remember to change the username to a new one every time you run the tests
      #!!!
      Examples:
            | userName                | password     |
            | TestLucia65           | TestPassword1! |


  Scenario: Generate Token using POST
    Given The API endpoint for generating a token is "https://demoqa.com/Account/v1/GenerateToken"
    When The request for generating the token is sent with method "POST"
    Then The response status code should be 200
    And The response body should contain "token", "expires", "status", and "result" fields

  Scenario: GET User
    Given The API endpoint for getting a user is "https://demoqa.com/Account/v1/User/{UUID}"
    When I send a GET request to the user endpoint with UUID and I have a header called Authorization which contains the token
    Then The response status code should be 200
    And The response body should contain "userId", "username", a list of "books" fields

  Scenario: GET Books
    Given The API endpoint for getting books is "https://demoqa.com/BookStore/v1/Books"
    When I send a GET request to the books endpoint
    Then The response status code should be 200
    Then The response body should contain a list of books

  Scenario: POST Books
    Given The API endpoint for adding books to a user's profile is "https://demoqa.com/BookStore/v1/Books"
    When The user chooses a random book by its isbn
    And I send a POST request and I have a header called Authorization which contains the token
    Then The response status code should be 201

  Scenario: Update Book using PUT
    Given The API endpoint for updating a book is "https://demoqa.com/BookStore/v1/Books/{ISBN}"
    When Another book is chosen randomly by its isbn
    And The request for updating is sent with method PUT and I have a header called Authorization which contains the token
    Then The response status code should be 200

  Scenario: DELETE Book
    Given The API endpoint for deleting a book is "https://demoqa.com/BookStore/v1/Book"
    When The request is sent with method DELETE and I have a header called Authorization which contains the token
    Then The response status code should be 204

  Scenario: GET Book
    Given The API endpoint for getting a book is "https://demoqa.com/BookStore/v1/Book"
    When The user chooses a random book by its isbn
    And I send a GET request and the isbn is added to the endpoint
    Then The response status code should be 200





