package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ListResult<T> implements Serializable {

    private PageInfo pageInfo;
    private int code;
    private String message;
    private List<T> data;
}