package org.example.controller;

import org.springframework.web.client.RestClientException;

public class APIException extends RestClientException {

    public APIException(String msg) {
        super(msg);
    }
}
