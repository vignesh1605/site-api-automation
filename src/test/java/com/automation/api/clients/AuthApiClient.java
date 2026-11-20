package com.automation.api.clients;

import com.automation.api.core.RequestSpecFactory;
import com.automation.api.models.request.LoginRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuthApiClient {

    public Response login(LoginRequest credentials) {
        return RequestSpecFactory.request()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post("/auth/login");
    }

    public Response currentUser(String accessToken) {
        return RequestSpecFactory.request()
                .auth().oauth2(accessToken)
                .get("/auth/me");
    }
}