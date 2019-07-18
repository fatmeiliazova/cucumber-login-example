package com.qa.test.API;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login {

    private static Logger LOG = LoggerFactory.getLogger(Login.class);

    public static String getJwtTokenForAUser(User user) {
        RequestSpecification request = Request.builder().build();
        request.body(user.toJsonString());
        ValidatableResponse response = request.post("/login").then();
        response.assertThat().statusCode(200);
        return response.extract().body().path("token");
    }
}
