package org.example.test.setup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import io.restassured.response.Response;

public class PostService extends BaseService {

    public List<String> getPostsByUserId(String user) throws IOException {
        Response response = get("/posts?userId=" + user);
        
        List<String> postIDList = new ArrayList<>();
        for (JsonNode node : Utility.constructListFromResponseString(Utility.getResponseContent(response))) {
            if (node.get("userId").asInt() == Integer.parseInt(user)) {
                postIDList.add(node.get("id").asText());
            }
        }
        return postIDList;
    }
}

