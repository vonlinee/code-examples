package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OptResult implements Serializable {

    private static final long serialVersionUID = -6899059737730837772L;

    private int code;
    private String message;
}