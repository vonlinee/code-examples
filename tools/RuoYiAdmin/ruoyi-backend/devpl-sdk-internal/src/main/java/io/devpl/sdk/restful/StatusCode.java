package io.devpl.sdk.restful;

import io.devpl.sdk.enumx.KeyedEnumPool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 响应状态编码
 */
public final class StatusCode implements Serializable {

    private static final long serialVersionUID = 8188825397312417945L;

    /**
     * 响应编码
     */
    private int code;

    /**
     * 描述信息
     */
    private String message;

    private StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[Status] " + code + " " + message;
    }

    /**
     * 枚举常量池
     */
    private static final KeyedEnumPool<Integer, StatusCode> pool = new KeyedEnumPool<>() {
        @Override
        public StatusCode put(Integer key, StatusCode instance) {
            StatusCode oldStatus = enumerations.put(key, instance);
            if (oldStatus == null) {
                return instance;
            }
            return oldStatus;
        }
    };

    public static StatusCode valueOf(int code, String message, boolean putIfNotExists) {
        StatusCode status = pool.get(code);
        if (status == null) {
            if (putIfNotExists) {
                return pool.put(code, new StatusCode(code, message));
            }
        }
        throw new NoSuchElementException(String.format("状态码[%s]不存在", code));
    }

    public static StatusCode valueOf(int code, String message) {
        return valueOf(code, message, true);
    }

    public static StatusCode valueOf(int code) {
        return valueOf(code, "", false);
    }

    /**
     * 更新状态码定义
     *
     * @param code
     * @param message
     * @param putIfNotExists
     */
    public static void update(int code, String message, boolean putIfNotExists) {
        StatusCode status = pool.get(code);
        if (status == null) {
            if (putIfNotExists) {
                pool.put(code, new StatusCode(code, message));
            }
        }
    }

    public static void add(int code, String message, boolean update) {
        StatusCode status = pool.get(code);
        if (status != null) {
            if (update) {
                status.setCode(code);
                status.setMessage(message);
            }
        }
    }

    public static List<StatusCode> listAll() {
        return new ArrayList<>(pool.values());
    }

    /**
     * 预定义的常量
     */
    public static final StatusCode HTTP_200 = valueOf(200, "响应正常");
    public static final StatusCode HTTP_404 = valueOf(404, "资源不存在");
    public static final StatusCode HTTP_301 = valueOf(301, "资源的URI已被更新 Moved Permanently");
    public static final StatusCode HTTP_500 = valueOf(500, "服务器内部异常");

    /**
     * 业务异常
     */
    public static final StatusCode UNCORRECT_PASSWORD = valueOf(10000, "密码错误");
    public static final StatusCode NO_PASSWORD = valueOf(10001, "请输入密码");
    public static final StatusCode TOKEN_EXPIRED = valueOf(10001, "用户TOKEN已过期");
}
