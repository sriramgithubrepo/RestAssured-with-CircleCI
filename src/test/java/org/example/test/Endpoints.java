package org.example.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String user = "/users";
    private static final String post = "/posts";
    private static final String comments = "/comments";
    public static final String username="Samantha";

    public static RequestSpecification reqBuilder(String BASE_URL) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        return request;
    }
    public static Response getResponse(String BASE_URL) {
        Response response = reqBuilder(BASE_URL).get();
        return response;
    }
    public static boolean isAPICallSuccess(Response response) {
        int code = response.getStatusCode();
        if( code == 200 ) return true;
        return false;
    }
    public static String getResponseContent(Response response) {
        return response.getBody().asString();
    }
    public static String getUsersURL(){
        return BASE_URL+ user;
    }
    public static String getPostsURL(){
        return BASE_URL+ post;
    }
    public static String getCommentsURL(){
        return BASE_URL+ comments;
    }
}
