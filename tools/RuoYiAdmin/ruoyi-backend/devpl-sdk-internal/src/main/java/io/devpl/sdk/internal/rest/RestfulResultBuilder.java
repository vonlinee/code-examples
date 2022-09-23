package io.devpl.sdk.internal.rest;

import io.devpl.sdk.internal.Builder;

interface RestfulResultBuilder<T extends RestfulResultBuilder<T>> extends Builder<RestfullResult, RestfulResultBuilder<T>> {

    T code(int code);

    T message(String message);

    T stackTrace(String stackTrace);

    T description(String description);

    T throwable(Throwable throwable);


    T setCode(int code);

    T setMessage(String message);

    T setStackTrace(String stackTrace);

    T setDescription(String description);

    T setThrowable(Throwable throwable);
}
