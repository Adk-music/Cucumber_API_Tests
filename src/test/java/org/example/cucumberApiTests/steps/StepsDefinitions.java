package org.example.cucumberApiTests.steps;

import data.ApiResponseWithError;
import data.ApiResponseWithUser;
import data.Error;
import data.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.File;
import java.util.List;
import java.util.Set;


public class StepsDefinitions {

    private static final String BASE_URL = "https://gorest.co.in/public-api";
    private static final String token = "3d109bb10acfb41f044fe9873794d854294671c0f46210bf82195d6a119af4d0";
    private static final String limitedToken = "37047c463c2e2978de9567754ba2ae8eee2970901ef80eb8b25228bb4322fc18";
    private static Response response;
    private static RequestSpecification request;
    private static String userId;


    @Given("Set up rest client")
    public void setUpRestClient() {
        request = RestAssured.given()
                .baseUri(BASE_URL);
    }

    @And("Authorization")
    public void authorization() {
        request = request.auth()
                .oauth2(token);
    }

    @When("Get list of users")
    public void when() {
        response = request.when().get("/users");

    }

    @When("Add new user parameters")
    public void addNewUserParameters() {
        User user = new User("Jakob Kuki", "male", "jkuk11199@abc.com", "active");
        response = request
                .contentType("application/json")
                .body(user)
                .when()
                .post("/users");
        List<User> users = response.as(ApiResponseWithUser.class).getData();
        User responseUser = users.get(0);
        userId = responseUser.getId();
        System.out.println(userId);
        System.out.println(response.body().prettyPrint());
    }

    @When("Update User parameters")
    public void updateUserParameters() {
        User user = new User(userId, "Jakob Kuki", "male", "jkukikuk98@abc.com", "active");
        response = request
                .contentType("application/json")
                .body(user)
                .when()
                .put("/users/" + userId);
    }

    @When("Put invalid User data")
    public void putInvalidUserData() {
        response = request
                .contentType("application/json")
                .body("email: 123kukud@abc.com")
                .when()
                .put("/users/" + userId);
    }

    @When("Try to put png media type")
    public void tryToPutPngMediaType() {
        File file = new File("src/main/java/data/test.png");
        response = request
                .contentType("png")
                .body(file)
                .when()
                .put("/users/" + userId);
    }

    @When("Try to post large media data")
    public void tryToPostLargeMediaData() {
        response = request
                .contentType("image/jpeg")
                .body(new File("src/main/java/data/test.png"))
                .when()
                .post("/users/");
    }

    @When("Try to post jpeg file")
    public void tryToPostJpegFile() {
        response = request
                .contentType("image/jpeg")
                .body(new File("src/main/java/data/test2.jpeg"))
                .when()
                .post("/users/");
    }

    @When("Try to put multiple requests")
    public void tryToPutMultipleRequests() {
        int count = 3;
        for (int i = 0; i < count; i++) {
            response = request.auth()
                    .oauth2(limitedToken)
                    .when()
                    .get("/users");
            System.out.println(response.getStatusCode());
        }
    }

    @When("Delete user data")
    public void deleteUserData() {
        response = request
                .when()
                .delete("/users/" + userId);
    }

    @When("Try to change User data without authorization")
    public void tryToChangeUserDataWithoutAuthorization() {
        response = request
                .when()
                .post("/users");
    }

    @When("Get non-existent user")
    public void getNonExistentUser() {
        response = request.when().get("/users/12345");
    }

    @When("Create Unprocessable data")
    public void createUnprocessableData() {
        response = request
                .contentType("application/json")
                .body("{\"name\": \"Jakob Kuki\"}")
                .when()
                .post("/users/");
    }

    @Then("Validate Status Code is: {int}")
    public void then(int expectedStatus) {
        ApiResponseWithUser apiResponseWithUSer = response.as(ApiResponseWithUser.class);
        int code = apiResponseWithUSer.getCode();
        Assert.assertEquals("Status code:" + code, expectedStatus, code);
    }

    @Then("Validate Error Status Code is: {int} and {string}")
    public void thenErrorStatusCode(int expectedStatus, String expectedMessage) {
        ApiResponseWithError apiResponseWithError = response.as(ApiResponseWithError.class);
        int code = apiResponseWithError.getCode();
        Error errorData = apiResponseWithError.getErrors().get(0);
        String actualMessage = errorData.getMessage();
        Assert.assertEquals("Status code:" + code, expectedStatus, code);
        Assert.assertEquals("Message is:" + actualMessage, expectedMessage, actualMessage);
    }

    @Then("Validate Http Error Status Code: {int}")
    public void thenRequestEntityTooLarge(int expectedStatus) {
        Assert.assertEquals("Status code:" + response.statusCode(), expectedStatus, response.statusCode());
    }

    @Then("Validate Errors for missing post fields")
    public void thenErrorForMissingPostFields() {
        ApiResponseWithError apiResponseWithError = response.as(ApiResponseWithError.class);
        int code = apiResponseWithError.getCode();
        List<Error> errors = apiResponseWithError.getErrors();
        Assert.assertEquals("Status code:" + code, 422, code);
        Set<String> missingFields = Set.of("email", "name", "gender", "status");
        errors.forEach(error -> {
            Assert.assertTrue(missingFields.contains(error.getField()));
            Assert.assertEquals("can't be blank", error.getMessage());
        });


    }


}

