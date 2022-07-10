package org.example.test.testCase;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.example.test.setUp.Endpoints;
import org.example.test.setUp.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestVerifyUser {
    private List<String> userLists;
    private static Response response;
    public static int userID=0;

    @BeforeEach
    public void setUpConnectionToGetUsers(){
        response= Utility.getResponse(Endpoints.getUsersURL());
    }
    @Test
    public void isUserExists(){
        //Assert.assertEquals(response.getStatusCode(), 200);
        if(Utility.isAPICallSuccess(response)) {
            userLists = response.getBody().jsonPath().getList("username");
            System.out.println(userLists);
            assertThat(userLists, hasItems(Endpoints.getUserName()));
        }
        else{
           Assertions.fail("Get User API call failed");
        }
    }
    @Test
    public void  getUserID() throws IOException{
        String resp = response.getBody().asString();
        for (JsonNode node : Utility.constructListFromResponseString(resp)) {
            if(node.get("username").asText().equals(Endpoints.getUserName())){
                userID= node.get("id").asInt();
            }
        }
      if(userID==0){
         Assertions.fail("User not exists");
      }
    }
}
