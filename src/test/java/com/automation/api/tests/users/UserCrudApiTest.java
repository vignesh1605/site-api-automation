package com.automation.api.tests.users;

import com.automation.api.clients.UserApiClient;
import com.automation.api.data.UserTestData;
import com.automation.api.models.request.UserRequest;
import com.automation.api.models.response.UserResponse;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserCrudApiTest {

    private static final int EXISTING_USER_ID = 1;
    private final UserApiClient users = new UserApiClient();

    @Test(dataProvider = "validCreateUsers", dataProviderClass = UserTestData.class)
    public void shouldCreateUser(UserRequest request) {
        UserResponse response = users.createUser(request)
                .then()
                .statusCode(201)
                .extract()
                .as(UserResponse.class);

        assertNotNull(response.id(), "The response should contain a user ID");
        assertEquals(response.firstName(), request.firstName(), "First name mismatch");
        assertEquals(response.lastName(), request.lastName(), "Last name mismatch");
    }

    @Test
    public void shouldReadExistingUser() {
        users.getUser(EXISTING_USER_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(EXISTING_USER_ID))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/user-response.schema.json"));
    }

    @Test
    public void shouldUpdateExistingUser() {
        UserRequest request = new UserRequest("Maya", "Sharma");

        users.updateUser(EXISTING_USER_ID, request)
                .then()
                .statusCode(200)
                .body("id", equalTo(EXISTING_USER_ID))
                .body("firstName", equalTo(request.firstName()))
                .body("lastName", equalTo(request.lastName()));
    }

    @Test
    public void shouldDeleteExistingUser() {
        users.deleteUser(EXISTING_USER_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(EXISTING_USER_ID))
                .body("isDeleted", equalTo(true));
    }

    @Test
    public void shouldReturnNotFoundForMissingUser() {
        int missingUserId = 999_999;

        users.getUser(missingUserId)
                .then()
                .statusCode(404)
                .body("message", not(blankOrNullString()));
    }

}
