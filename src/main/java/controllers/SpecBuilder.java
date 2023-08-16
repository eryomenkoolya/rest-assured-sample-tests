package controllers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static utilities.ConfigProvider.getConfigParameter;

public class SpecBuilder {

    public static RequestSpecification setupRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(getConfigParameter("base.url", ""))
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .build();
    }

    public static ResponseSpecification setupResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}
