package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import engine.RestResponse;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import models.Album;
import models.Post;
import models.User;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static utilities.ConfigProvider.getConfigParameter;

public class UserController extends BaseController {

    public static final String USERS_URL = getConfigParameter("users.url", "");

    public RestResponse<List<User>> getUsers() {
        Response response = sendGetRequest(USERS_URL)
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<List<User>> getUsers(Map<String, String> queryParams) {
        Response response = sendGetRequest(USERS_URL, queryParams)
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<User> getUser(int userId) {
        Response response = sendGetRequest(format("%s/%s", USERS_URL, userId))
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<List<Album>> getUserAlbums(int userId) {
        Response response = sendGetRequest(format("%s/%s/albums", USERS_URL, userId))
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<List<Post>> getUserPosts(int userId) {
        Response response = sendGetRequest(format("%s/%s/posts", USERS_URL, userId))
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }
}
