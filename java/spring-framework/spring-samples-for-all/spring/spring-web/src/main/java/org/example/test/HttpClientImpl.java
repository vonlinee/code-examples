package org.example.test;

import org.example.bean.Employee;
import org.example.bean.Result;
<<<<<<< HEAD
import org.example.controller.APIException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
=======
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
>>>>>>> e8fc4946246422a6bfa56b5335dbdab7e026f947
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.factory.CoreReflectionFactory;
import sun.reflect.generics.factory.GenericsFactory;
import sun.reflect.generics.repository.FieldRepository;
import sun.reflect.generics.scope.ClassScope;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> e8fc4946246422a6bfa56b5335dbdab7e026f947

@Service
public class HttpClientImpl implements HttpClient {

    @Resource
    private RestTemplate restTemplate;

    @Override
<<<<<<< HEAD
    public <T> T getForObject(String url, Class<T> type) throws APIException {
        ParameterizedTypeReference<Result<T>> typeReference = makerParameterizedTypeReference(type);
        ResponseEntity<Result<T>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), typeReference);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
            throw new APIException("响应实体为NULL");
        }
        if (!responseEntity.hasBody()) {
            throw new APIException("响应实体为NULL");
=======
    public <T> T getForObject(String url, Class<T> type) throws RestClientException {
        CustomRequestCallback requestCallback = new CustomRequestCallback(new HttpEntity<>(null), type);
        requestCallback.setTemplate(restTemplate);

        // Result<Employee> result = restTemplate.getForObject(url, Result.class);
        // Employee employee = result.getData();  // ClassCastException

        ResponseExtractor<ResponseEntity<Result<T>>> responseExtractor = restTemplate.responseEntityExtractor(type);
        ResponseEntity<Result<T>> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);
        if (responseEntity == null) {
            throw new RuntimeException();
>>>>>>> e8fc4946246422a6bfa56b5335dbdab7e026f947
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
<<<<<<< HEAD
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
=======
    public <T> List<T> getForList(String url, Class<T> type) {
        String signature = getSignature(type);
        Type parameterizedType = make(signature, Result.class);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null);

        Type entityType = new ParameterizedTypeReference<Result<List<T>>>() {
        }.getType();

        /**
         * @see RestTemplate#exchange(RequestEntity, ParameterizedTypeReference)
         */
        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestEntity, entityType);
        ResponseExtractor<ResponseEntity<Result<List<T>>>> responseExtractor = restTemplate.responseEntityExtractor(entityType);
        ResponseEntity<Result<List<T>>> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor, (Object) null);
        assert responseEntity != null;
        Result<List<T>> body = responseEntity.getBody();
        assert body != null;
        return body.getData();
    }

    public static String getSignature(Class<?> type) {
        String name = type.getName().replace(".", "/");
        // String signature = "Lorg/example/bean/Result<Ljava/util/List<Lorg/example/bean/Employee;>;>;";
        return "Lorg/example/bean/Result<Ljava/util/List<L" + name + ";>;>;";
    }

    /**
     * 手动制作ParameterizedType
     *
     * @param genericSignature 泛型签名，字段的泛型签名
     * @param declaringClass   字段声明所在的类
     * @return 泛型类型，即ParameterizedType
     */
    public static Type make(String genericSignature, Class<?> declaringClass) {
        // 获取GenericsFactory
        GenericsFactory genericsFactory = CoreReflectionFactory.make(declaringClass, ClassScope.make(declaringClass));
        // 构造FieldRepository
        FieldRepository fieldRepository = FieldRepository.make(genericSignature, genericsFactory);
        return fieldRepository.getGenericType();
>>>>>>> e8fc4946246422a6bfa56b5335dbdab7e026f947
    }
}
