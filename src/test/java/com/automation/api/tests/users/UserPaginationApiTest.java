package com.automation.api.tests.users;

import com.automation.api.clients.UserApiClient;
import com.automation.api.hooks.ApiTestHooks;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class UserPaginationApiTest extends ApiTestHooks {

    private final UserApiClient users = new UserApiClient();

    @Test
    public void shouldReturnRequestedSliceOfUsers() {
        int secondUserId = users.listUsers(2, 0)
                .then()
                .statusCode(200)
                .body("users", hasSize(2))
                .body("users[1].id", notNullValue())
                .extract()
                .path("users[1].id");

        users.listUsers(1, 1)
                .then()
                .statusCode(200)
                .body("skip", equalTo(1))
                .body("limit", equalTo(1))
                .body("users", hasSize(1))
                .body("users[0].id", equalTo(secondUserId));
    }

    @Test
    public void shouldReturnEmptyPageAtEndOfUserList() {
        int total = users.listUsers(1, 0)
                .then()
                .statusCode(200)
                .body("total", greaterThan(0))
                .extract()
                .path("total");

        users.listUsers(1, total)
                .then()
                .statusCode(200)
                .body("skip", equalTo(total))
                .body("users", hasSize(0));
    }
}