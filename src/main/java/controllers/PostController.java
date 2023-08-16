package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import engine.RestResponse;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import models.Post;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static utilities.ConfigProvider.getConfigParameter;

public class PostController extends BaseController {

    public static final String POSTS_URL = getConfigParameter("posts.url", "");

    public RestResponse<List<Post>> getPosts() {
        Response response = sendGetRequest(POSTS_URL)
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<List<Post>> getPosts(Map<String, String> queryParams) {
        Response response = sendGetRequest(POSTS_URL, queryParams)
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<Post> getPost(int postId) {
        Response response = sendGetRequest(format("%s/%s", POSTS_URL, postId))
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<Post> createPost(Post post) {
        Response response = sendPostRequest(POSTS_URL, post)
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<Post> updatePost(Post post) {
        Response response = sendPutRequest(format("%s/%s", POSTS_URL, post.getId()), post)
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }

    public RestResponse<Post> deletePost(int postId) {
        Response response = sendDeleteRequest(format("%s/%s", POSTS_URL, postId))
                .then()
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .response();
        return new RestResponse<>(new TypeReference<>() {
        }, response);
    }
}
