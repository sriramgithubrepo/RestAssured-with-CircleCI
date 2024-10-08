package org.example.test.setup;

import io.restassured.response.Response;
import java.util.List;

public class CommentService extends BaseService {

    // Method to get comments for a specific post
    public List<Comment> getCommentsForPost(String postId) {
        // Sending GET request to fetch comments for the given postId
        Response response = get("/comments?postId=" + postId);
        
        // Retrieving the whole list of Comment objects from the response
        return response.jsonPath().getList("", Comment.class);
    }
}

