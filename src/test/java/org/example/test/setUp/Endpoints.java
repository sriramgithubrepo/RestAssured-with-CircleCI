package org.example.test.setUp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
/**@description This Class contains API endpoints and methods to invoke them */

public class Endpoints {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String user = "/users";
    private static final String post = "/posts";
    private static final String comments = "/comments";
    private static final String username="Samanthaaa";

    public static String getUsersURL(){
        return BASE_URL+ user;
    }
    public static String getPostsURL(){
        return BASE_URL+ post;
    }
    public static String getCommentsURL(){
        return BASE_URL+ comments;
    }
    public static String getUserName(){
        return username;
    }

}
