package com.automation.api.auth;

import com.automation.api.clients.AuthApiClient;
import com.automation.api.models.request.LoginRequest;
import com.automation.api.models.response.LoginResponse;
import io.restassured.response.Response;

public class TokenProvider {

    private final AuthApiClient authApi;

    public TokenProvider(AuthApiClient authApi) {
        this.authApi = authApi;
    }

    public String accessToken(LoginRequest credentials) {
        Response response = authApi.login(credentials);

        if (response.statusCode() != 200) {
            throw new IllegalStateException(
                    "Could not obtain access token. Login returned HTTP "
                            + response.statusCode());
        }

        String token = response.as(LoginResponse.class).accessToken();

        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Login response is missing accessToken");
        }

        return token;
    }
}