package org.example.test.setup;

import io.restassured.response.Response;

public class UserService extends BaseService {

    public String getUserByUsername(String username) {
    Response response = get("/users?username=" + username);
       String output = response.jsonPath().get("id").toString();
       String userID = output.replaceAll("[\\[\\]]", "");
        return userID;
    }
}
