package io.devpl.webui.rest;

import io.maker.base.utils.AbstractConstant;
import io.maker.base.utils.AttributeKey;
import io.maker.base.utils.ConstantPool;

import java.util.function.Function;

public final class RespStatus<T> extends AbstractConstant<RespStatus<T>> {

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

    private static final ConstantPool<RespStatus<Object>> pool = new ConstantPool<RespStatus<Object>>() {
        @Override
        protected RespStatus<Object> newConstant(int id, String name) {
            return new RespStatus<>(id, name);
        }
    };

    /**
     * Creates a new instance.
     * @param id
     * @param name
     */
    protected RespStatus(int id, String name) {
        super(id, name);
    }

    @SuppressWarnings("unchecked")
    public static <T> RespStatus<T> valueOf(T code, String message) {
        RespStatus<T> status = (RespStatus<T>) pool.valueOf(nameOf(code));
        status.code = code;
        status.message = message;
        return status;
    }

    public static <T> boolean exists(T code) {
        return pool.exists(nameOf(code));
    }

    @SuppressWarnings("unchecked")
    public static <T> RespStatus<T> put(T code, String message) {
        RespStatus<T> status = (RespStatus<T>) pool.newInstance(nameOf(code));
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
    public static final RespStatus<Integer> HTTP_200 = valueOf(200, "响应正常");
    public static final RespStatus<Integer> HTTP_404 = valueOf(404, "资源不存在");
    public static final RespStatus<Integer> HTTP_500 = valueOf(500, "服务器内部异常");
    public static final RespStatus<Integer> HTTP_3XX = valueOf(300, "重定向");
}
