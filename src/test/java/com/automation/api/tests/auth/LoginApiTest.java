package com.automation.api.tests.auth;

import com.automation.api.auth.TokenProvider;
import com.automation.api.clients.AuthApiClient;
import com.automation.api.config.ConfigManager;
import com.automation.api.models.request.LoginRequest;
import com.automation.api.models.response.LoginResponse;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class LoginApiTest {

    private final AuthApiClient authApi = new AuthApiClient();
    private final TokenProvider tokenProvider = new TokenProvider(authApi);

    @Test(groups = "auth")
    public void shouldReturnAccessTokenForValidCredentials() {
        LoginResponse response = authApi.login(credentials())
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        String token = response.accessToken();
        assertTrue(token != null && !token.isBlank(),
                "Successful login should return an access token");
    }

    @Test(groups = "auth")
    public void shouldReturnCurrentUserWithBearerToken() {
        LoginRequest credentials = credentials();
        String token = tokenProvider.accessToken(credentials);

        authApi.currentUser(token)
                .then()
                .statusCode(200)
                .body("username", equalTo(credentials.username()));
    }

    private LoginRequest credentials() {
        return new LoginRequest(
                ConfigManager.apiUsername(),
                ConfigManager.apiPassword());
    }
}