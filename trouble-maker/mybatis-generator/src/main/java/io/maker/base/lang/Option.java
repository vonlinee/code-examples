package io.maker.base.lang;

import java.util.HashMap;
import java.util.Map;

public enum Option {

    OK, ERROR;

    private String message;

    private Object data;

    private static final Map<String, Object> map = new HashMap<>();

}
