package io.devpl.sdk.restful;

public class Result<T> extends ResultfulResultTemplate implements ResultBuilder<T> {

    private T data;

    Result() {
        super();
    }

    Result(StatusCode status, String toast) {
        this(status.getCode(), status.getMessage(), null, toast);
    }

    Result(int code, String message) {
        this(code, message, null, null);
    }

    Result(int code, String message, String toast) {
        this(code, message, null, toast);
    }

    Result(int code, String message, T data, String toast) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
        this.toast = toast;
    }

    @Override
    public Result<T> build() {
        if (this.code == 0) this.code = -1;
        if (this.message == null) this.message = "";
        if (this.toast == null) this.toast = "";
        if (this.stackTrace == null) this.stackTrace = "";
        return this;
    }

    @Override
    public ResultBuilder<T> setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public ResultBuilder<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public ResultBuilder<T> setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    @Override
    public ResultBuilder<T> setToast(String toastMessage) {
        this.toast = toastMessage;
        return this;
    }

    @Override
    public ResultBuilder<T> setData(T data) {
        this.data = data;
        return this;
    }
}
