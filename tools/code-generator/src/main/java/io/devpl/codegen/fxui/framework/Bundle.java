package io.devpl.codegen.fxui.framework;

import java.util.Map;

public class Bundle {

    private Map<String, Object> map;

    public <T> T get(String name) {
        return (T) map.get(name);
    }
}
