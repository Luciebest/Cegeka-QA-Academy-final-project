# project-lucia-mertic

## The goal of the project is to exercise UI testing an REST API testing on the bookstore app

The UI is available here
```bash
https://demoqa.com/books
```

The swagger for the REST API is available here
```bash
https://demoqa.com/swagger/#/
```
## Languages, frameworks, libraries used
- Java 17
- Selenium 4.3 for UI testing
- Cucumber 7.11.1
- Spring Core, Spring test 5.3.24
- rest-assured 4.3.3 for REST api testing
- Spring JDBC template for retrieving test users from a mysql local db, as a pre-requisites for the tests
  
### UI
- The framework will use the BDD approach. I used Cucumber scenarios for the UI and API tests.
- We must support cross browser testing. We must be able to run test with different browsers:Chrome, Microsoft Chromium EDGE.
- If a UI scenario fails, we need a screenshot when the step failed.
- We must separate the UI elements that are specific to certain screens or UI components in separate page classes that will be Spring @Component.
- We'll let Spring handle page objects creation. We do not need to return the next page when calling a method that is navigating to a new page.
- The Page classes must handle interaction with web elements. We do not expose WebElements outside the page classes.
- No asserts should be performed in the page classes.
- The Step definitions classes must retrain from calling driver specific methods. They must use methods available from page classes and other helper classes.
- Avoided hardcoding properties all over the place, for example the starting base url of the book's app. I made use of Spring @Value (e.g. @Value("${base.url:}")) and put the value in a configuration file.
- In **phase 1,** the user needed in order to perform the test,can be loaded from a configuration file.
  
### API
- Decode response payloads in Java POJO( plain old java objects), that act as a model class, in favor of using json path.
- Observed some parts of al calls have common similar setup, so I created a class to handle this.

### SQL users test data retrieval **phase2**
- If in phase 1 the user was retrieved from a configuration file, in this phase our goal is to retrieve the user from a local user mysql database
- We'll use Spring JDBC Template for retrieving a test user
- The database table will be populated by calling the REST API user POST method. See the swagger for more details https://demoqa.com/swagger/#/Account/AccountV1UserPost
```bash
POST  https://demoqa.com/Account/v1/User
```
### Scenario requirements
## UI
- the scope is for the books store application
- the highest priority scenario to write and automate(implement) is the end to end happy flow of logging in, selecting a book, adding a book to your collection, and check the book is present on the profile, then deleting it
- write other scenarios without implementing them, at first. Tag them with @prio1, @prio2. *both are implemented now!
- implemented them by starting with prio1, then moved to prio2
- prio1 : User logs in and tries to delete their account
- prio2: User registers on the website
## API
Cover
- Generate Token using POST https://demoqa.com/Account/v1/GenerateToken
- GET user "https://demoqa.com/Account/v1/User/{UUID}"
- GET books https://demoqa.com/BookStore/v1/Books
- POST books https://demoqa.com/BookStore/v1/Books
- Update book using PUT https://demoqa.com/BookStore/v1/Books/{ISBN}
- DELETE book https://demoqa.com/BookStore/v1/Book
- GET book https://demoqa.com/BookStore/v1/Book
