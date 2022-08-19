package org.example.springboot.rest;

import java.util.Map;

public class Result extends AbstractResult<Map<String, Object>> {

    @Override
    protected String serialize() {
        return null;
    }
}
