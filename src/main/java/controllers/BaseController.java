package controllers;

import io.restassured.response.Response;

import java.util.Map;

import static controllers.SpecBuilder.setupRequestSpec;
import static io.restassured.RestAssured.given;

public class BaseController {

    protected Response sendGetRequest(String url) {
        return given()
                .spec(setupRequestSpec())
                .when()
                .get(url);
    }

    protected Response sendGetRequest(String url, Map<String, String> queryParams) {
        return given()
                .spec(setupRequestSpec())
                .queryParams(queryParams)
                .when()
                .get(url);
    }

    protected Response sendPostRequest(String url, Object body) {
        return given()
                .spec(setupRequestSpec())
                .body(body)
                .when()
                .post(url);
    }

    protected Response sendPutRequest(String url, Object body) {
        return given()
                .spec(setupRequestSpec())
                .body(body)
                .when()
                .put(url);
    }

    protected Response sendDeleteRequest(String url) {
        return given()
                .spec(setupRequestSpec())
                .when()
                .delete(url);
    }
}
