package com.automation.api.clients;

import com.automation.api.core.RequestSpecFactory;
import com.automation.api.models.request.UserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserApiClient {

    public Response createUser(UserRequest user){
        return RequestSpecFactory.request()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/users/add");
    }

    public Response getUser(int id){
        return RequestSpecFactory.request()
                .pathParam("id",id)
                .get("/users/{id}");
    }

    public Response updateUser(int id, UserRequest user){
        return RequestSpecFactory.request()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .body(user)
                .patch("/users/{id}");
    }

    public Response deleteUser(int id){
        return RequestSpecFactory.request()
                .pathParam("id",id)
                .delete("/users/{id}");
    }

    public Response listUsers(int limit, int skip) {
        return RequestSpecFactory.request()
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .get("/users");
    }

}
