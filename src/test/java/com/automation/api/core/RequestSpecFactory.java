package com.automation.api.core;

import com.automation.api.config.ConfigManager;
import com.automation.api.logging.ApiLog;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestSpecFactory {

    private RequestSpecFactory() {
    }
    public static RequestSpecification request() {
        return given()
                .baseUri(ConfigManager.baseUrl())
                .accept(ContentType.JSON)
                .filter(ApiLog.httpFilter());
    }
}
