package org.example.test.setup;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseService {
    protected RequestSpecification requestSpec;

    public BaseService() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com/")
                .setContentType(ContentType.JSON)
                .build();
    }

    protected Response get(String endpoint) {
        return RestAssured.given(requestSpec).get(endpoint);
    }
}
