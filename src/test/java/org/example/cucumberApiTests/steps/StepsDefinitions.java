package org.example.cucumberApiTests.steps;

import data.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;


public class StepsDefinitions {

    private static final String BASE_URL = "https://gorest.co.in/public/v2";
    private static final String token = "3d109bb10acfb41f044fe9873794d854294671c0f46210bf82195d6a119af4d0";
    private static Response response;
    private static RequestSpecification request;
    private static String userId;


    @Given("Set up rest client")
    public void setUpRestClient() {
        request = RestAssured.given()
                .baseUri(BASE_URL);
    }

    @When("Get list of users")
    public void when() {
        response = request.when().get("/users");

    }

    @When("Add new user parameters")
    public void addNewUserParameters() {
        User user = new User("Jakob Kuki", "male", "jkuk1112222@abc.com", "active");
        response = request.auth()
                .oauth2(token)
                .contentType("application/json")
                .body(user)
                .when()
                .post("/users");
        User responseUser = response.as(User.class);
        userId = responseUser.getId();
        System.out.println(userId);
        System.out.println(response.body().prettyPrint());
    }

    @When("Update User parameters")
    public void updateUserParameters() {
        User user = new User(userId, "Jakob Kuki", "male", "jkukikuk@abc.com", "active");
        response = request.auth()
                .oauth2(token)
                .contentType("application/json")
                .body(user)
                .when()
                .put("/users/" + userId);
        System.out.println(response.body().prettyPrint());
    }

    @When("Delete user data")
    public void deleteUserData() {
        response = request.auth()
                .oauth2(token)
                .when()
                .delete("/users/" + userId);

    }

    @When("Put invalid User data")
    public void putInvalidUserData() {
        response = request.auth()
                .oauth2(token)
                .contentType("application/json")
                .body("email: 123kukud@abc.com")
                .when()
                .put("/users/5149");
    }

    @When("Get non-existent user")
    public void getNonExistentUser() {
        response = request.when().get("/users/12345");
    }

    @Then("Validate Status Code is: {int}")
    public void then(int expectedStatus) {
        Assert.assertEquals("Status code:" + response.getStatusCode(), expectedStatus, response.getStatusCode());
    }

}

