package io.devpl.webui.rest;

import lombok.Data;

@Data
public class EntityResultTemplate extends ResultTemplate {

    private Object data;

    @Override
    public String serialize() {
        return null;
    }
}
