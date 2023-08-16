package engine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.response.Response;

import java.io.IOException;

public class RestResponse<T> {

    private static final ObjectMapper MAPPER = new ObjectMapper() {{
        registerModule(new JavaTimeModule());
    }};

    private T data;
    private final Response response;
    private Exception exception;

    public RestResponse(TypeReference<T> typeReference, Response response) {
        this.response = response;
        try {
            this.data = MAPPER.readValue(response.getBody().asInputStream(), typeReference);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    public String getContent() {
        return response.getBody().asString();
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public boolean isSuccessful() {
        int code = response.getStatusCode();
        return code >= 200 && code <= 205;
    }

    public String getStatusDescription() {
        return response.getStatusLine();
    }

    public Response getResponse() {
        return response;
    }

    public T getBody() {
        return data;
    }

    public Exception getException() {
        return exception;
    }

    public JsonNode getJsonFromResponseContent() throws IOException {
        return MAPPER.readTree(getContent());
    }
}
