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
    Then Validate Status Code is: 400

  Scenario: Unauthorized User
    Given Set up rest client
    When Try to change User data without authorization
    Then Validate Status Code is: 401

  Scenario: Get list of users "Not Found"
    Given Set up rest client
    When Get non-existent user
    Then Validate Status Code is: 404

  Scenario: Create User with unprocessable Entity data
    Given Set up rest client
    And Authorization
    When Create Unprocessable data
    Then Validate Status Code is: 422