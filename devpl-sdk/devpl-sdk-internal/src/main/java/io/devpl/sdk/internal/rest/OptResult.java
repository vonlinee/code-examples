package io.devpl.sdk.internal.rest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class OptResult extends RestfulResultTemplate implements Serializable {

    private static final long serialVersionUID = -6899059737730837772L;

}