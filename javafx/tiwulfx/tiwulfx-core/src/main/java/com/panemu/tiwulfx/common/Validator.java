package com.panemu.tiwulfx.common;

public interface Validator<T> {

    /**
     * Validate the value.
     * @param value the value.
     * @return null if the value is valid. Otherwise, return invalid message
     */
    String validate(T value);
}
