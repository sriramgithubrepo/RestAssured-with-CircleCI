package org.example.test.testcase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.response.Response;
import org.example.test.setup.EndPoints;
import org.example.test.setup.Utility;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestCases {
    private static Response response;
    private static List<Integer> postIDList;
    private static int userID;

    @Test
    @Order(1)
    /**@description This method verifies if we can get all the users */
    public void getUsers() {
        response = Utility.getResponse(EndPoints.getUsersURL());
        if (Utility.isAPICallSuccess(response)) {
            response.getBody().jsonPath().getList("username");
        } else {
            //Assert.fail("Get User API call failed");
            Assertions.fail("Get User API Call failed");
        }
    }

    @Test
    @Order(2)
    /**@description This method verifies if specific user "Samantha" exists in the users list */
    public void verifyUserExists() throws IOException {
        String resp = Utility.getResponseContent(response);
        for (JsonNode node : Utility.constructListFromResponseString(resp)) {
            if (node.get("username").asText().equals(EndPoints.getUserName())) {
                userID = node.get("id").asInt();
                break;
            }
        }
        if (userID == 0) {
            Assertions.fail("User does not exists");
        }
    }

    @Test
    @Order(3)
    /**@description This method verifies there are posts for the user "Samantha" */
    public void verifyPostExists() throws IOException {
            postIDList = new ArrayList<Integer>();
            response = Utility.getResponse(EndPoints.getPostsURL());
        if (userID > 0) {
            if (Utility.isAPICallSuccess(response)) {
                String resp = Utility.getResponseContent(response);
                for (JsonNode node : Utility.constructListFromResponseString(resp)) {
                    if (node.get("userId").asInt() == userID) {
                        postIDList.add(node.get("id").asInt());
                    }
                }
                if (postIDList.size() == 0) {
                    Assertions.fail("No Post available for the user");
                }
            } else {
                Assertions.fail("Get Posts API call failed");
            }
        }else{
            Assertions.fail("User does not exists, hence no post available");
        }
    }

    @Test
    @Order(4)
    /**@description This method verifies if there are comment for a post and validates the email format */
    public void verifyEmailFormat() throws IOException {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        if (postIDList != null && postIDList.size() > 0) {
            for (Integer id : postIDList) {
                Response response = Utility.reqBuilder(EndPoints.getCommentsURL()).
                        queryParam("postId", id).get();
                if (Utility.isAPICallSuccess(response)) {
                    String resp = Utility.getResponseContent(response);
                    ArrayNode commentsList = Utility.constructListFromResponseString(resp);
                    if (commentsList.size() > 0) {
                        for (JsonNode node : commentsList) {
                            String email = node.get("email").asText();
                            Matcher matcher = pattern.matcher(email);
                            System.out.println("PostID:" + id + ". Email to validate: " + email + " Is Valid? :" + matcher.matches());
                        }
                    } else {
                        Assertions.fail("No comments for the " + id);
                    }
                } else {
                    Assertions.fail("Get comments for a specific Post call failed");
                }
            }
        } else {
            Assertions.fail("User does not exists,hence no email to validate");
        }
    }
}

