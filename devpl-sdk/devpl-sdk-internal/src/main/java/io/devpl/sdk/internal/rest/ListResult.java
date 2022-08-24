package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ListResult<T> extends RestfulResultTemplate implements Serializable {

    private static final long serialVersionUID = -497037305528002807L;

    private PageInfo pageInfo;
    private List<T> data;
}