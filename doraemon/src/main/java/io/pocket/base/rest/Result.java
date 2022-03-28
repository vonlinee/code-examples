package io.pocket.base.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象结果类
 * @param <T>
 */
abstract class Result<T> implements Serializable {

    private static final long serialVersionUID = 2819999455116368072L;

    private final Map<String, String> map = new HashMap<>();

    protected String timestamp;
    protected String message;
    protected ResultDescription description;
    protected T data;

    Result() {
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    Result(ResultDescription description) {
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.description = description;
    }

    public final String message() {
        return message;
    }

    public final T data() {
        return data;
    }

    /**
     * 展示结果，类似于toString方法
     * @return 建议使用JSON字符串或者其他比较好的格式
     */
    protected abstract String show();

    final String wrapQuotation(String str) {
        if (!str.contains("\"")) {
            return "\"" + str + "\"";
        } else {
            if (str.startsWith("\"") && !str.endsWith("\"")) return str + "\"";
            if (!str.startsWith("\"") && str.endsWith("\"")) return str + "\"";
            String substring = str.substring(1, str.length() - 1);
            if (substring.contains("\"")) {
                return "\"" + substring.replace("\"", "") + "\"";
            }
            return str;
        }
    }
}
