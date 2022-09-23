package io.devpl.sdk.restful;

import io.devpl.sdk.ExtendedBuilder;

interface RestfulResultBuilder<R, RB extends RestfulResultBuilder<R, RB>> extends ExtendedBuilder<R, RB> {

    default RB code(int code) {
        return setCode(code);
    }

    default RB message(String message) {
        return setMessage(message);
    }

    default RB stackTrace(String stackTrace) {
        return setStackTrace(stackTrace);
    }

    default RB toast(String toastMessage) {
        return setToast(toastMessage);
    }

    RB setCode(int code);

    RB setMessage(String message);

    RB setStackTrace(String stackTrace);

    RB setToast(String toastMessage);

    default RB status(StatusCode status) {
        return code(status.getCode()).message(status.getMessage());
    }
}
