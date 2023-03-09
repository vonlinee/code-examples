package org.example.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RestTemplate日志拦截器
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    Logger log = LoggerFactory.getLogger(CustomRequestCallback.class);

    /**
     * @param request   the request, containing method, URI, and headers
     * @param body      the body of the request
     * @param execution the request execution
     * @return ClientHttpResponse
     */
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, byte @NotNull [] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        // 打印日志
        // String responseStr = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        // log.info("URI        : {}, \n" + "Method     : {}, \n" + "Headers    : {}, \n" + "RespStatus : {}, \n" + "Response   : {}", request.getURI(), request.getMethod(), request.getHeaders(), response.getStatusCode(), responseStr);
        return response;
    }
}