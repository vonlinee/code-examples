package org.example.test;

import org.springframework.web.client.RestClientException;

/**
 * @see org.springframework.web.client.RestOperations
 */
public interface HttpClient {

    <T> T getFotObject(String url, Class<T> type) throws RestClientException;
}
