package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class ListResult<T> extends RestfulResultTemplate implements Serializable {

    private static final long serialVersionUID = -497037305528002807L;

    private PageInfo pageInfo;
    private List<T> data;

    interface InternalBuilder<T> extends io.devpl.sdk.internal.rest.Builder<ListResult<T>> {
        void setData(T data);
    }

