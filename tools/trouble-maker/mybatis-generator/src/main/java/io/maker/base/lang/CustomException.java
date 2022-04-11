package io.maker.base.lang;

/**
 * 每次都要new一个异常对象
 */
public class CustomException extends RuntimeException {

    private static final CustomException EMPTY = new CustomException();

    public static CustomException getInstance() {
        return EMPTY;
    }

    private long timestamp; // 异常产生时间戳
    private String message;
    private Throwable throwable;

    public static CustomException wrap(Throwable throwable) {
        return new CustomException();
    }

    public boolean isUnChecked() {
        return throwable instanceof RuntimeException || isError();
    }

    public boolean isException() {
        return throwable instanceof Exception;
    }

    public boolean isError() {
        return throwable instanceof Error;
    }

    public static void main(String[] args) {
        try {
            method();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void method() {
        throw CustomException.EMPTY;
    }
}
