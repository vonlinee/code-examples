package io.devpl.sdk.internal.enumx;

import lombok.Data;

@Data
public class StatusCode implements TypedEnum<StatusCode> {

    private int code;
    private String message;

    @Override
    public StatusCode value() {
        return this;
    }

    @Override
    public String name() {
        return this.code + this.message;
    }

    @Override
    public String id() {
        return String.valueOf(code);
    }
}
