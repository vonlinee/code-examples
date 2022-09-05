package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.*;

@Data
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class ListResult<E> extends Result implements Serializable {

    private static final long serialVersionUID = -497037305528002807L;

    private PageInfo pageInfo;

    private List<Object> data;
}

