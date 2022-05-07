package sample.spring.boot.token.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;
}
