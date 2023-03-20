package org.example.test;

import org.example.bean.Result;
import org.example.controller.APIException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class HttpClientImpl implements HttpClient {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public <T> T getForObject(String url, Class<T> type) throws APIException {
        ParameterizedTypeReference<Result<T>> typeReference = makerParameterizedTypeReference(type);
        ResponseEntity<Result<T>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), typeReference);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
            throw new APIException("响应实体为NULL");
        }
        if (!responseEntity.hasBody()) {
            throw new APIException("响应实体为NULL");
        }
        Result<T> result = responseEntity.getBody();
        if (result == null) {
            throw new APIException("响应实体为NULL");
        }
        if (result.getCode() != 200) {
            throw new APIException("接口异常");
        }
        return result.getData();
    }

    @Override
    public <T> List<T> getForList(String url, Class<T> type) throws APIException {
        ParameterizedTypeReference<Result<List<T>>> typeReference = makerParameterizedTypeReferenceForList(type);
        ResponseEntity<Result<List<T>>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), typeReference);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
            throw new APIException("响应实体为NULL");
        }
        if (!responseEntity.hasBody()) {
            throw new APIException("响应实体为NULL");
        }
        Result<List<T>> result = responseEntity.getBody();
        if (result == null) {
            throw new APIException("响应实体为NULL");
        }
        if (result.getCode() != 200) {
            throw new APIException("接口异常");
        }
        return result.getData();
    }

    private <T> ParameterizedTypeReference<Result<T>> makerParameterizedTypeReference(Class<T> clazz) {
        MyParameterizedType type = MyParameterizedType.make(Result.class, new Type[]{clazz}, Result.class.getDeclaringClass());
        return ParameterizedTypeReference.forType(type);
    }

    private <T> ParameterizedTypeReference<Result<List<T>>> makerParameterizedTypeReferenceForList(Class<T> clazz) {
        MyParameterizedType type = MyParameterizedType.make(Result.class, new Type[]{List.class, clazz}, Result.class.getDeclaringClass());
        return ParameterizedTypeReference.forType(type);
    }
}
