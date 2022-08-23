package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EntityResult<T> implements Serializable {

    private static final long serialVersionUID = -3021643689165802865L;

    private int code;
    private String message;
    private T data;
}