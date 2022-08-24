package io.devpl.sdk.internal.rest;

import lombok.Data;

@Data
public abstract class Result extends ResultTemplate {

    private static final long serialVersionUID = -7013151316748848493L;

    private int code;
    private String message;
    private String description;
    private String stacktrace;
    private Object data;
}
