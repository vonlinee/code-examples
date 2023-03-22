package io.devpl.toolkit.common;

/**
 * 响应结果生成工具
 */
public class Results {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static <T> Result<T> of() {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> of(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
        return result;
    }

    public static <T> Result<T> fail(String message) {
        return new Result<T>()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
}
