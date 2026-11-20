package com.automation.api.tests;

import com.automation.api.core.RequestSpecFactory;
import com.automation.api.hooks.ApiTestHooks;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SmokeApiTest extends ApiTestHooks {

    @Test(groups = "smoke")
    public void apiShouldReturnOk() {
        RequestSpecFactory.request()
                .when()
                .get("/test")
                .then()
                .statusCode(200)
                .body("status", equalTo("ok"));
    }
}
