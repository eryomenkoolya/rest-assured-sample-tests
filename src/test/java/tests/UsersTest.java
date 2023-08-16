package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import controllers.UserController;
import engine.RestResponse;
import models.Album;
import models.Post;
import models.User;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.fge.jackson.JsonLoader.fromFile;
import static com.github.fge.jsonschema.main.JsonSchemaFactory.byDefault;
import static org.assertj.core.api.Assertions.assertThat;

public class UsersTest {

    private final UserController userController = new UserController();
    private static final int EXISTENT_USER_ID = 2;

    @Test
    public void getAllUsers() {
        RestResponse<List<User>> response = userController.getUsers();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void validateJsonSchema() throws IOException, ProcessingException {
        RestResponse<List<User>> response = userController.getUsers();
        JsonNode jsonToValidate = response.getJsonFromResponseContent();
        JsonNode schemaNode = fromFile(new File("src/test/resources/users-schema.json"));
        ProcessingReport validate = byDefault().getJsonSchema(schemaNode).validate(jsonToValidate);
        assertThat(validate.isSuccess()).isTrue();
    }

    @Test
    public void getExistentUserById() {
        RestResponse<User> response = userController.getUser(EXISTENT_USER_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody().getId()).isEqualTo(EXISTENT_USER_ID);
    }

    @Test
    public void getAllUserAlbums() {
        RestResponse<List<Album>> response = userController.getUserAlbums(EXISTENT_USER_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).extracting(Album::getUserId).containsOnly(EXISTENT_USER_ID);
    }

    @Test
    public void getAllUserPosts() {
        RestResponse<List<Post>> response = userController.getUserPosts(EXISTENT_USER_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).extracting(Post::getUserId).containsOnly(EXISTENT_USER_ID);
    }
}
