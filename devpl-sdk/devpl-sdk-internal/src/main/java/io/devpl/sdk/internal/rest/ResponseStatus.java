package io.devpl.sdk.internal.rest;

import io.devpl.sdk.internal.AbstractConstant;
import io.devpl.sdk.internal.ConstantPool;

/**
 * 状态码常量
 * @param <T>
 */
public final class ResponseStatus<T> extends AbstractConstant<ResponseStatus<T>> {

    /**
     * 唯一业务编码
     */
    private T code;

    /**
     * 提示信息
     */
    private String message;

    public T getCode() {
        return code;
    }

    public void setCode(T code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static final ConstantPool<ResponseStatus<Object>> pool = new ConstantPool<ResponseStatus<Object>>() {
        @Override
        protected ResponseStatus<Object> newConstant(int id, String name) {
            return new ResponseStatus<>(id, name);
        }
    };

    /**
     * Creates a new instance.
     * @param id
     * @param name
     */
    protected ResponseStatus(int id, String name) {
        super(id, name);
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseStatus<T> valueOf(T code, String message) {
        ResponseStatus<T> status = (ResponseStatus<T>) pool.valueOf(nameOf(code));
        status.code = code;
        status.message = message;
        return status;
    }

    public static <T> boolean exists(T code) {
        return pool.exists(nameOf(code));
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseStatus<T> put(T code, String message) {
        ResponseStatus<T> status = (ResponseStatus<T>) pool.newInstance(nameOf(code));
        status.code = code;
        status.message = message;
        return status;
    }

    @Override
    public int id() {
        return super.id();
    }

    @Override
    public String name() {
        return nameOf(code);
    }

    public static <T> String nameOf(T code) {
        return "status-" + code;
    }

    /**
     * 常见的状态码
     */
    public static final ResponseStatus<Integer> HTTP_200 = valueOf(200, "响应正常");
    public static final ResponseStatus<Integer> HTTP_404 = valueOf(404, "资源不存在");
    public static final ResponseStatus<Integer> HTTP_500 = valueOf(500, "服务器内部异常");
    public static final ResponseStatus<Integer> HTTP_3XX = valueOf(300, "重定向");
}
