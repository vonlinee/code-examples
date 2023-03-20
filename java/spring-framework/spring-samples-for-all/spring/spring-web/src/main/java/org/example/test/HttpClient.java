package org.example.test;

import org.example.controller.APIException;

import java.util.List;

import java.util.List;

/**
 * @see org.springframework.web.client.RestOperations
 */
public interface HttpClient {

<<<<<<< HEAD
    <T> T getForObject(String url, Class<T> type) throws APIException;

    <T> List<T> getForList(String url, Class<T> type) throws APIException;
=======
    <T> T getForObject(String url, Class<T> type) throws RestClientException;

    <T> List<T> getForList(String url, Class<T> type);
>>>>>>> e8fc4946246422a6bfa56b5335dbdab7e026f947
}
