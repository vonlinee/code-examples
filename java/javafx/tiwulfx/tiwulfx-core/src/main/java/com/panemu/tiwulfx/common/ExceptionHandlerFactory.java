package com.panemu.tiwulfx.common;

public interface ExceptionHandlerFactory {

    /**
     * A method where {@link ExceptionHandler} is instantiated and returned.
     * @return an implementation of {@link ExceptionHandler}
     */
    ExceptionHandler createExceptionHandler();
}
