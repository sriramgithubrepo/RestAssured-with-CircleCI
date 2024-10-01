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
public class TestCases2 {
    private static Response response;
    private static List<Integer> postIDList;
    private static int userID;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Test
    @Order(1)
    @DisplayName("Verify fetching all users")
    public void getUsers() {
        response = Utility.getResponse(EndPoints.getUsersURL());
        if (!Utility.isAPICallSuccess(response)) {
            Assertions.fail("Get User API Call failed");
        }
    }

    @Test
    @Order(2)
    @DisplayName("Verify specific user exists in the users list")
    public void verifyUserExists() throws IOException {
        String resp = Utility.getResponseContent(response);
        for (JsonNode node : Utility.constructListFromResponseString(resp)) {
            if (node.get("username").asText().equals(EndPoints.getUserName())) {
                userID = node.get("id").asInt();
                break;
            }
        }
        Assertions.assertTrue(userID > 0, "User does not exist");
    }

    @Test
    @Order(3)
    @DisplayName("Verify posts exist for the user")
    public void verifyPostExists() throws IOException {
        if (userID <= 0) {
            Assertions.fail("User does not exist, hence no posts available");
            return;
        }

        response = Utility.getResponse(EndPoints.getPostsURL());
        if (!Utility.isAPICallSuccess(response)) {
            Assertions.fail("Get Posts API call failed");
        }

        postIDList = new ArrayList<>();
        String resp = Utility.getResponseContent(response);
        for (JsonNode node : Utility.constructListFromResponseString(resp)) {
            if (node.get("userId").asInt() == userID) {
                postIDList.add(node.get("id").asInt());
            }
        }
        Assertions.assertFalse(postIDList.isEmpty(), "No posts available for the user");
    }

    @Test
    @Order(4)
    @DisplayName("Verify email format in comments for posts")
    public void verifyEmailFormat() throws IOException {
        if (postIDList == null || postIDList.isEmpty()) {
            Assertions.fail("No posts available for email validation");
            return;
        }

        for (Integer postId : postIDList) {
            Response response = Utility.reqBuilder(EndPoints.getCommentsURL())
                    .queryParam("postId", postId)
                    .get();

            if (!Utility.isAPICallSuccess(response)) {
                Assertions.fail("Get comments API call failed for postId: " + postId);
            }

            ArrayNode commentsList = Utility.constructListFromResponseString(Utility.getResponseContent(response));
            for (JsonNode comment : commentsList) {
                String email = comment.get("email").asText();
                Matcher matcher = EMAIL_PATTERN.matcher(email);
                Assertions.assertTrue(matcher.matches(), "Invalid email format for PostID: " + postId + ". Email: " + email);
            }
        }
    }
}
