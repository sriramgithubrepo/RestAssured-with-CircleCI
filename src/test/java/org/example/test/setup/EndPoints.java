package org.example.test.setup;

/**@description This Class contains API endpoints and methods to invoke them */

public class EndPoints {
    private static final String baseUrl = "https://jsonplaceholder.typicode.com";
    private static final String user = "/users";
    private static final String post = "/posts";
    private static final String comments = "/comments";
    private static final String userName="Samantha";

    public static String getUsersURL(){
        return baseUrl+ user;
    }
    public static String getPostsURL(){
        return baseUrl+ post;
    }
    public static String getCommentsURL(){
        return baseUrl+ comments;
    }
    public static String getUserName(){
        return userName;
    }

}
