package com.automation.api.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SmokeApiTest {

    @Test(groups = "smoke")
    public void apiShouldReturnOk() {
        given()
                .baseUri("https://dummyjson.com")
                .when()
                .get("/test")
                .then()
                .statusCode(200)
                .body("status", equalTo("ok"));
    }
}
