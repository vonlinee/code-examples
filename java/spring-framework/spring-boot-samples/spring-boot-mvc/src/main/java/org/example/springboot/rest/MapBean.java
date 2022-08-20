package org.example.springboot.rest;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MapBean {

    private String id;

    private Map<String, Object> fields = new HashMap<>();

}
