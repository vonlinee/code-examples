package io.devpl.spring.web.mvc;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Map;

/**
 * 不能使用Map，会被其他的参数解析器处理
 */
@Data
public class RequestInfo implements Serializable {

    private String path;
    private String token;
    private HttpMethod method;
    private Map<String, String[]> param;
    private Map<String, Object> body;
    private HttpHeaders headers;

}
