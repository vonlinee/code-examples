package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

@Data
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class ListResult<E> extends RestfulResultTemplate implements Serializable {

    private static final long serialVersionUID = -497037305528002807L;

    private PageInfo pageInfo;

    private List<E> data;
}

