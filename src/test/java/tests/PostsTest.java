package tests;

import controllers.PostController;
import engine.RestResponse;
import models.Post;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.Post.createDummyPost;
import static org.assertj.core.api.Assertions.assertThat;

public class PostsTest {

    private final PostController postController = new PostController();
    private static final int EXISTENT_POST_ID = 55;
    private static final int NON_EXISTENT_POST_ID = 99999;
    private static final int EXISTENT_USER_ID = 1;

    @Test
    public void getAllPosts() {
        RestResponse<List<Post>> response = postController.getPosts();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void getAllPostsWithUserId() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", String.valueOf(EXISTENT_USER_ID));
        RestResponse<List<Post>> response = postController.getPosts(queryParams);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).extracting(Post::getUserId).containsOnly(EXISTENT_USER_ID);
    }

    @Test
    public void getExistentPostById() {
        RestResponse<Post> response = postController.getPost(EXISTENT_POST_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody().getId()).isEqualTo(EXISTENT_POST_ID);
    }

    @Test
    public void getNonExistentPostById() {
        RestResponse<Post> response = postController.getPost(NON_EXISTENT_POST_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void createPost() {
        Post postToCreate = createDummyPost();
        RestResponse<Post> response = postController.createPost(postToCreate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(postToCreate, "id");
    }

    //    *Important*: resource will not be really updated on the server but it will be faked as if.
//    So no need to undo made changes
    @Test
    public void updatePostWithNewUserId() {
        int newUserId = 83;
        RestResponse<Post> initialPost = postController.getPost(EXISTENT_POST_ID);
        RestResponse<Post> updatedPost = postController.updatePost(initialPost.getBody().toBuilder().userId(newUserId).build());
        assertThat(updatedPost.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(updatedPost.getBody().getUserId()).isEqualTo(newUserId);
    }

    //    *Important*: resource will not be really deleted on the server but it will be faked as if.
//    So no need to undo made changes
    @Test
    public void deletePost() {
        RestResponse<Post> response = postController.deletePost(EXISTENT_POST_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}
