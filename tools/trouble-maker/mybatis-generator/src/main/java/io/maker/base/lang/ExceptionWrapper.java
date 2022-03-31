package io.maker.base.lang;

public class ExceptionWrapper {

    Throwable exception;

    public static Exception wrap(Throwable throwable) {
        if (throwable instanceof Error) {

        }
        return null;
    }
}
