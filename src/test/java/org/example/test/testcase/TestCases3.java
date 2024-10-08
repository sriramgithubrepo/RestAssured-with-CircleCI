package org.example.test.testcase;
import java.io.IOException;
import java.util.List;
import org.example.test.setup.Comment;
import org.example.test.setup.CommentService;
import org.example.test.setup.EmailValidator;
import org.example.test.setup.PostService;
import org.example.test.setup.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;;

@TestInstance(Lifecycle.PER_CLASS)
public class TestCases3 {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;
    private EmailValidator emailValidator;

    @BeforeAll
    public void setup() {
        userService = new UserService();
        postService = new PostService();
        commentService = new CommentService();
        emailValidator = new EmailValidator();
    }

private String getUserIdByUsername(String username)  {
    String user = userService.getUserByUsername(username);
    Assertions.assertNotNull(user, "User not found!");
    return user;
}

// Helper method to get posts by user ID
private List<String> getPostsByUserId(String userId) throws IOException {
    List<String> posts = postService.getPostsByUserId(userId);
    Assertions.assertFalse(posts.isEmpty(), "No posts found for the user!");
    return posts;
}


@Test
public void verifyUserExists() throws IOException {
    String user = getUserIdByUsername("Samantha");
    System.out.println("The user ID is: " + user);
}

@Test
public void verifyPostExistsForUser() throws IOException {
    String user = getUserIdByUsername("Samantha");
    List<String> posts = getPostsByUserId(user);
    System.out.println("The List of Posts are: " + posts);
}

@Test
public void testEmailValidationForComments() throws IOException {
    String user = getUserIdByUsername("Samantha");
    List<String> posts = getPostsByUserId(user);

    for (String post : posts) {
        System.out.println("Post ID: " + post);
        List<Comment> comments = commentService.getCommentsForPost(post);
        Assertions.assertFalse(comments.isEmpty(), "No comments found for post ID: " + post);

        for (Comment comment : comments) {
            System.out.println("Email to check: " + comment.getEmail());
            Assertions.assertTrue(emailValidator.isValid(comment.getEmail()),
                    "Invalid email: " + comment.getEmail());
        }
    }
}
}

