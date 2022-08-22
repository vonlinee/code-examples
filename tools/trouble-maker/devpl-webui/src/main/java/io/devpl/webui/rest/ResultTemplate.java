package io.devpl.webui.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ResultTemplate参与序列化
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ResultTemplate extends Result<Object> implements Serializable {

    protected int code;
    protected String message;
    protected String description;

    public abstract String serialize();

    static abstract class Builder {
        protected int code;
        protected String message;
        protected String description;

        abstract ResultTemplate build();
    }
}
