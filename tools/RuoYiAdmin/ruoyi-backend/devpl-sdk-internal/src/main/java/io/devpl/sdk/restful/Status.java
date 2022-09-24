package io.devpl.sdk.restful;

import io.devpl.sdk.enumx.KeyedEnumPool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 响应状态编码
 * 1.全部返回200: 不管对错，一律返回200，在返回的JSON中再具体指明错误的原因
 * 2.按照规范：使用规范的HTTP状态码。如果是没有登录，就返回401，如果是没权限就返回403。
 * @since 0.0.1
 */
public final class Status implements Serializable {

    private static final long serialVersionUID = 8188825397312417945L;

    /**
     * 响应编码
     */
    private int code;

    /**
     * 描述信息
     */
    private String message;

    private Status(int code, String message) {
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
    private static final KeyedEnumPool<Integer, Status> pool = new KeyedEnumPool<>() {
        @Override
        public Status put(Integer key, Status instance) {
            Status oldStatus = enumerations.put(key, instance);
            if (oldStatus == null) {
                return instance;
            }
            return oldStatus;
        }
    };

    public static Status valueOf(int code, String message, boolean putIfNotExists) {
        Status status = pool.get(code);
        if (status == null) {
            if (putIfNotExists) {
                return pool.put(code, new Status(code, message));
            }
        }
        throw new NoSuchElementException(String.format("状态码[%s]不存在", code));
    }

    /**
     * 默认不存在时会创建并放入常量池
     *
     * @param code    响应状态码
     * @param message 响应信息
     * @return Status
     */
    public static Status valueOf(int code, String message) {
        return valueOf(code, message, true);
    }

    public static Status valueOf(int code) {
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
        Status status = pool.get(code);
        if (status == null) {
            if (putIfNotExists) {
                pool.put(code, new Status(code, message));
            }
        }
    }

    public static void add(int code, String message, boolean update) {
        Status status = pool.get(code);
        if (status != null) {
            if (update) {
                status.setCode(code);
                status.setMessage(message);
            }
        }
    }

    public static List<Status> listAll() {
        return new ArrayList<>(pool.values());
    }

    /**
     * 预定义的常量
     */
    public static final Status HTTP_200 = valueOf(200, "OK");
    public static final Status HTTP_201 = valueOf(201, "created");
    public static final Status HTTP_202 = valueOf(202, "accepted");
    public static final Status HTTP_400 = valueOf(400, "bad request");
    public static final Status HTTP_401 = valueOf(401, "未登录");
    public static final Status HTTP_404 = valueOf(404, "not found");
    public static final Status HTTP_405 = valueOf(405, "unsupported media type");
    public static final Status HTTP_301 = valueOf(301, "moved permanently");
    public static final Status HTTP_500 = valueOf(500, "internal server error");
    public static final Status HTTP_503 = valueOf(503, "Service Unavailable");

    /**
     * 业务异常
     */
    public static final Status UNCORRECT_PASSWORD = valueOf(10000, "密码错误");
    public static final Status NO_PASSWORD = valueOf(10001, "请输入密码");
    public static final Status TOKEN_EXPIRED = valueOf(10001, "用户TOKEN已过期");
}
