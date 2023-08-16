package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder(toBuilder = true)
@Jacksonized
public class Post {

    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    public static Post createDummyPost() {
        return Post.builder()
                .userId(Integer.parseInt(randomNumeric(3)))
                .title("Title of the test post " + randomAlphanumeric(10))
                .body("Body of the test post " + randomAlphanumeric(50))
                .build();
    }
}
