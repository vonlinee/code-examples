package org.example.test;

import org.example.bean.Result;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class HttpClientImpl implements HttpClient {

    @Resource
    RestTemplate restTemplate;

    @Override
    public <T> T getFotObject(String url, Class<T> type) throws RestClientException {
        CustomRequestCallback requestCallback = new CustomRequestCallback(new HttpEntity<>(null), type);
        requestCallback.setTemplate(restTemplate);
        ResponseExtractor<ResponseEntity<Result<T>>> responseExtractor = restTemplate.responseEntityExtractor(type);
        ResponseEntity<Result<T>> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);
        if (responseEntity == null) {
            throw new RuntimeException();
        }
        Result<T> result = responseEntity.getBody();
        if (result == null) {
            throw new RuntimeException();
        }
        return result.getData();
    }
}
