package org.example.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.Employee;
import org.example.bean.Result;
import org.example.test.HttpClient;
import org.example.test.LoggingRequestInterceptor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/test")
public class HttpbinController {

    private ServerProperties serverInfo;

    public HttpbinController(ServerProperties serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Resource
    RestTemplate template;

    @Resource
    HttpClient client;

    @GetMapping("/get")
    public void get1() {
        template.setInterceptors(Collections.singletonList(new LoggingRequestInterceptor()));
        // 要加上完整地址
        String addr = "http://localhost:" + serverInfo.getPort();
        // Result<List<Employee>> forObject = template.getForObject(addr + "/api/employee/list", Result.class);
        //
        // assert forObject != null;
        // List<Employee> data = forObject.getData();
        //
        // // 泛型不正确
        // ResponseEntity<Result<List>> list = template.exchange(addr + "/api/employee/list", HttpMethod.GET, new HttpEntity<>(null), makerParameterizedTypeReference(List.class));
        //
        // // 泛型正确
        // ResponseEntity<Result<List<Employee>>> entity = template.exchange(addr + "/api/employee/list", HttpMethod.GET, new HttpEntity<>(null), new ParameterizedTypeReference<Result<List<Employee>>>() {
        //
        // });
        //
        // //System.out.println(entity);
        //
        // Employee result = client.getForObject(addr + "/api/employee/one", Employee.class);
        //
        // System.out.println(result);

        List<Employee> employees = client.getForList(addr + "/api/employee/list", Employee.class);

<<<<<<< HEAD
        Employee employee = get(addr + "/api/employee/one", Employee.class);

        // 泛型正确
        ResponseEntity<Result<List<Employee>>> entity = template.exchange(addr + "/api/employee/list", HttpMethod.GET, new HttpEntity<>(null), new ParameterizedTypeReference<Result<List<Employee>>>() {

        });

        //System.out.println(entity);

        Employee result = client.getForObject(addr + "/api/employee/one", Employee.class);

        List<Employee> list = client.getForList(addr + "/api/employee/list", Employee.class);
        System.out.println(result);
=======
        System.out.println(employees);
>>>>>>> e8fc4946246422a6bfa56b5335dbdab7e026f947
    }

    public <T> T get(String url, Class<T> type) {
        // 泛型不正确
        ResponseEntity<Result<T>> responseEntity = template.exchange(url, HttpMethod.GET, new HttpEntity<>(null), makerParameterizedTypeReference(type));

        // template.exchange(url, HttpMethod.GET, new HttpEntity<>(null), new MyParameterizedType(Result.class, new Type[]{type}, Result.class));
        return responseEntity.getBody().getData();
    }

    private <T> ParameterizedTypeReference<Result<T>> makerParameterizedTypeReference(Class<T> clazz) {
        ParameterizedTypeImpl type = ParameterizedTypeImpl.make(Result.class, new Type[]{clazz}, Result.class.getDeclaringClass());
        return ParameterizedTypeReference.forType(type);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private <T> ParameterizedTypeReference<Result<T>> getReference(Class<T> clazz) {
        // objectMapper已经缓存Type，不需要额外缓存
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Result.class, Result.class, clazz);
        return ParameterizedTypeReference.forType(javaType);
    }

    @GetMapping("/get2")
    public void get2() {
        // 要加上完整地址
        String addr = "http://localhost:" + serverInfo.getPort();
        Result<?> result = template.getForObject(addr + "/employee/list", Result.class);
        System.out.println(result);
    }
}
