package net.maku.generator.common.utils;

import lombok.Data;
import net.maku.generator.common.exception.StatusCode;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 响应数据
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class Result<T> {

    // 编码 0表示成功，其他值表示失败
    private int code = 0;
    // 消息内容
    private String msg = "success";
    // 响应数据
    private T data;

    private String stackTrace;

    public Result<T> message(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        return error(StatusCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> error(String msg) {
        return error(StatusCode.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(StatusCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> exception(Throwable throwable) {
        Result<T> result = error(throwable.getMessage());
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        result.stackTrace = sw.toString();
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
