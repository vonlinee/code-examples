package io.devpl.sdk.internal.rest;

import io.devpl.sdk.internal.AbstractConstant;
import io.devpl.sdk.internal.ConstantPool;

import java.util.Random;

/**
 * 状态码常量
 * @param <T>
 */
public final class StatusCode<T> extends AbstractConstant<StatusCode<T>> {

    private StatusCode() {
        super(new Random().nextInt(), "");
    }

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

    private static final ConstantPool<StatusCode<Object>> pool = new ConstantPool<StatusCode<Object>>() {
        @Override
        protected StatusCode<Object> newConstant(int id, String name) {
            return new StatusCode<>(id, name);
        }
    };

    /**
     * Creates a new instance.
     * @param id
     * @param name
     */
    private StatusCode(int id, String name) {
        super(id, name);
    }

    @SuppressWarnings("unchecked")
    public static <T> StatusCode<T> valueOf(T code, String message) {
        StatusCode<T> status = (StatusCode<T>) pool.valueOf(nameOf(code));
        status.code = code;
        status.message = message;
        return status;
    }

    public static <T> boolean exists(T code) {
        return pool.exists(nameOf(code));
    }

    @SuppressWarnings("unchecked")
    public static <T> StatusCode<T> put(T code, String message) {
        StatusCode<T> status = (StatusCode<T>) pool.newInstance(nameOf(code));
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
    public static final StatusCode<Integer> HTTP_200 = valueOf(200, "响应正常");
    public static final StatusCode<Integer> HTTP_404 = valueOf(404, "资源不存在");
    public static final StatusCode<Integer> HTTP_500 = valueOf(500, "服务器内部异常");
    public static final StatusCode<Integer> HTTP_3XX = valueOf(300, "重定向");

    /**
     * 业务定义状态码
     */
    public static final StatusCode<Integer> WRONG_PASSWORD = valueOf(200, "密码错误");
    public static final StatusCode<Integer> NO_PRIVELEGE = valueOf(200, "权限不足");
}
