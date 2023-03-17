package org.example.test;

import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * @see org.springframework.web.client.RestOperations
 */
public interface HttpClient {

    <T> T getForObject(String url, Class<T> type) throws RestClientException;

    <T> List<T> getForList(String url, Class<T> type);
}
