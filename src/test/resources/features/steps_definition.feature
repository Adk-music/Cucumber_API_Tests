Feature: Positive Tests

  Scenario: Get list of users
    Given Set up rest client
    When Get list of users
    Then Validate Status Code is: 200

  Scenario: Create new User
    Given Set up rest client
    When Add new user parameters
    Then Validate Status Code is: 201

  Scenario: Update User data
    Given Set up rest client
    When Update User parameters
    Then Validate Status Code is: 200

  Scenario: Delete User
    Given Set up rest client
    When Delete user data
    Then Validate Status Code is: 204

