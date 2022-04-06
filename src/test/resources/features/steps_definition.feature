Feature: Positive and Negative User Tests

  Scenario: Get list of users
    Given Set up rest client
    When Get list of users
    Then Validate Status Code is: 200

  Scenario: Create new User
    Given Set up rest client
    And Authorization
    When Add new user parameters
    Then Validate Status Code is: 201

  Scenario: Update User data
    Given Set up rest client
    And Authorization
    When Update User parameters
    Then Validate Status Code is: 200

  Scenario: Delete User
    Given Set up rest client
    And Authorization
    When Delete user data
    Then Validate Status Code is: 204

  Scenario: Invalid User data in the request
    Given Set up rest client
    And Authorization
    When Put invalid User data
    Then Validate Error Status Code is: 400 and "Error occurred while parsing request parameters"

  Scenario: Unauthorized User
    Given Set up rest client
    When Try to change User data without authorization
    Then Validate Error Status Code is: 401 and "Authentication failed"

  Scenario: Get list of users "Not Found"
    Given Set up rest client
    When Get non-existent user
    Then Validate Error Status Code is: 404 and "Resource not found"

  Scenario: Validate status Too Many Request
    Given Set up rest client
    When Try to put multiple requests
    Then Validate Error Status Code is: 429 and "Too many requests"

  Scenario: Put Large media file
    Given Set up rest client
    And Authorization
    When Try to post large media data
    Then Validate Http Error Status Code: 413

  Scenario: Put unsupported media type to User data
    Given Set up rest client
    And Authorization
    When Try to post jpeg file
    Then Validate Http Error Status Code: 500

  Scenario: Create User with unprocessable Entity data
    Given Set up rest client
    And Authorization
    When Create Unprocessable data
    Then Validate Errors for missing post fields