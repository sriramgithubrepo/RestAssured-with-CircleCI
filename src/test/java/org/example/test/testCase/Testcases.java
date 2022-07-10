package org.example.test.testCase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.response.Response;
import org.example.test.setUp.Endpoints;
import org.example.test.setUp.Utility;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Testcases {
    private List<String> userLists;
    private static Response response;
    private static List<Integer> postIDList=new ArrayList<Integer>();
    private static int userID=0;

    @Test
    @Order(1)
    public void getUsers(){
        response= Utility.getResponse(Endpoints.getUsersURL());
        if(Utility.isAPICallSuccess(response)) {
            userLists = response.getBody().jsonPath().getList("username");
        }
        else{
            //Assert.fail("Get User API call failed");
            Assertions.fail("Get User API Call failed");
        }
    }
    @Test
    @Order(2)
    public void  isUserExists() throws IOException{
        String resp = Utility.getResponseContent(response);
        for (JsonNode node : Utility.constructListFromResponseString(resp)) {
            if(node.get("username").asText().equals(Endpoints.getUserName())){
                userID= node.get("id").asInt();
                break;
            }
        }
        if(userID==0){
            Assertions.fail("User not exists");

        }
    }

    @Test
    @Order(3)
    public void isPostExists() throws IOException {
        response=Utility.getResponse(Endpoints.getPostsURL());
        if(Utility.isAPICallSuccess(response)) {
            String resp = Endpoints.getResponseContent(response);
            for (JsonNode node : Utility.constructListFromResponseString(resp)) {
                if (node.get("userId").asInt() == userID) {
                    postIDList.add(node.get("id").asInt());
                }
            }
            if (postIDList.size() == 0) {
                Assertions.fail("No Post available for the user");
            }
        }
        else{
            Assertions.fail("Get Post API call failed");
        }
    }
    @Test
    @Order(4)
    public void isEmailFormatValid() throws IOException {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        if(postIDList.size()>0) {
            for (Integer id : postIDList) {
                Response response = Utility.reqBuilder(Endpoints.getCommentsURL()).
                        queryParam("postId", id).get();
                if (Endpoints.isAPICallSuccess(response)) {
                    String resp = Utility.getResponseContent(response);
                    ArrayNode commentsList = Utility.constructListFromResponseString(resp);
                    if (commentsList.size() > 0) {
                        for (JsonNode node : commentsList) {
                            String email = node.get("email").asText();
                            Matcher matcher = pattern.matcher(email);
                            System.out.println("PostID:"+id + ". Email to validate: " + email + " Is Valid? :" + matcher.matches());
                        }
                    } else {
                        Assertions.fail("No comments for the " + id);
                    }
                }
                else{Assertions.fail("Get comments for a specific Post call failed");}
            }
        }
        else{
            Assertions.fail("No Post for the User");
        }
    }
}

