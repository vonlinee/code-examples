package io.devpl.sdk.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * @param <T> 携带的数据类型
 * @since 0.0.1
 */
public class Result<T> extends RestfulResultTemplate implements RBuilder<T> {

    /**
     * 存放具体的业务数据
     */
    private T data;

    Result() {
        super();
    }

    Result(Status status, String toast) {
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
        return this;
    }

    @Override
    public RBuilder<T> setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public RBuilder<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public RBuilder<T> setThrowable(Throwable throwable) {
        if (this.throwable == null || throwable != this.throwable) {
            this.stacktrace = getStackTrace(throwable);
        }
        this.throwable = throwable;
        return this;
    }

    @Override
    public RBuilder<T> setToast(String toastMessage) {
        this.toast = toastMessage;
        return this;
    }

    @Override
    public RBuilder<T> setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
        return this;
    }

    @Override
    public RBuilder<T> setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }

    @Override
    protected void toJSONString(StringBuilder result) {
        Gson gson = new Gson();
        JsonElement je = gson.toJsonTree(this.data);

    }
}