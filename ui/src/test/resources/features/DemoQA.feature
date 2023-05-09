Feature: DemoQA feature
# I recommend running each scenario one by one
# -Dcucumber.filter.tags="@prio0" in vm options


  #!!!
  #For prio0 (only...), we get a random user from the database
  #!!!
  @prio0
  Scenario: User logs in, selects a book, adds it to their collection, and deletes it
    Given Books page is opened
    Then All the books are displayed on the page and we save all the displayed info about them
    When The user clicks on the "Login" button
    Then Login page is opened

    When All users are retrieved from db and I save one of them randomly
    Then We enter valid credentials saved from the database
    #When The user enters valid credentials "<username>" and "<password>"
    And The login is attempted
    Then Books page is opened and the user is logged in

    When The user selects a book
    Then Details page is opened
    And Details of that book are displayed

    When The user clicks on the "Add To Your Collection" button
    Then An alert with the message "Book added to your collection" pops up

    When The user goes to the Profile Page
    Then The added book is displayed in the Profile Page

    When The user selects the added book and clicks on the "Delete" icon
    Then A modal with the message "Do you want to delete this book?" pops up
    And The user clicks on the OK button
    And An alert with the message "Book deleted" pops up
    Then The added book should be removed



#    Examples:
#      | username                | password     |
#      | Luciebest1           | Password1! |

  @prio1
  Scenario Outline: User logs in and tries to delete their account

    Given Books page is opened
    Then All the books are displayed on the page and we save all the displayed info about them
    When The user clicks on the "Login" button
    Then Login page is opened
    When The user enters valid credentials "<username>" and "<password>"
    And The login is attempted
    Then Books page is opened and the user is logged in

    When The user goes to the Profile Page
    When The user clicks on the "Delete Account" button
    Then A modal with the message "Do you want to delete your account?" pops up
    And The user clicks on the OK button
    Then An alert with the message "User Deleted" pops up
    And Login page is opened

    When The user enters valid credentials "<username>" and "<password>"
    And The login is attempted
    Then A text with an error message "Invalid username or password!" is displayed on the page

    #!!!!!
    #Create a user in swagger before running prio1
    #!!!!!
    Examples:
      | username  | password |
      | MariaIoana6  | Password100! |

  @prio2
  Scenario Outline: User registers on the website
    Given Books page is opened
    Then The user clicks on the "Login" button
    When The user clicks on the "New User" button
    Then Register page is opened

    When The user enters their "<firstname>", "<lastname>", "<username>" and "<password>"
    #!!!
    #The user has to solve the captcha manually and has 30 seconds to do so
    #!!!
    And The user checks the "I'm not a robot" checkbox
    And The user clicks on the "Register" button
    Then A new alert with the message "User Register Successfully." pops up

    When The user goes to the Login Page
    Then Login page is opened
    When The user enters valid credentials "<username>" and "<password>"
    And The login is attempted
    Then Profile page is opened and the user is logged in

    #!!!!
    #Remember to change the username to a new one every time you run @prio2
    # !!!!!
    Examples:
      | firstname  | lastname | username  | password|
      | John       | Doe    | JohnDoe251  | Password10! |

