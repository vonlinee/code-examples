/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah .
 */
package com.panemu.tiwulfx.common;

/**
 *
 * @author Amrullah 
 */
public interface Validator<T> {
    
    /**
     * Validate the value.
     * @param value
     * @return null if the value is valid. Otherwise return invalid message
     */
    String validate(T value);
    
}
