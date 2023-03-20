package org.example.test;

import org.example.controller.APIException;

import java.util.List;

/**
 * @see org.springframework.web.client.RestOperations
 */
public interface HttpClient {

    <T> T getForObject(String url, Class<T> type) throws APIException;

    <T> List<T> getForList(String url, Class<T> type) throws APIException;
}
