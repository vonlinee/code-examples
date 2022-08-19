package org.example.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数类型不匹配 MethodArgumentTypeMismatchException
     *
     * @param exception MethodArgumentTypeMismatchException
     * @param request   HttpServletRequest
     * @return ApiFinalResult
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result methodArgTypeMismatch(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        log.error("客户端异常，请求URL: {}", request.getServletPath(), exception);
        return new Result(StatusCode.FAILED_PARAMETER, "参数类型不匹配", "", 0);
    }

    /**
     * 缺少必要的请求参数
     *
     * @param exception MissingServletRequestParameterException
     * @param request   HttpServletRequest
     * @return ApiFinalResult
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        log.error("客户端异常，请求URL: {}", request.getServletPath(), exception);
        return new Result(StatusCode.FAILED_PARAMETER, "缺少必要的请求参数", "", 0);
    }

    /**
     * 参数解析失败或不传参数
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        log.error("客户端异常，请求URL: {}", request.getServletPath(), exception);
        return new Result(StatusCode.FAILED_PARAMETER, "参数错误", "", 0);
    }

    /**
     * 参数验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result bindException(BindException exception, HttpServletRequest request) {
        log.error("参数校验异常，请求URL: {}", request.getServletPath(), exception);
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                builder.append(error.getDefaultMessage()).append(';');
            }
            return new Result(StatusCode.FAILED_PARAMETER, "参数错误" + builder.toString(), "", 0);
        }
        return new Result(StatusCode.FAILED_PARAMETER, "参数错误", "", 0);
    }

    /**
     * 非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        log.error("非法参数异常，请求URL: {}", request.getServletPath(), exception);
        return new Result(StatusCode.FAILED_PARAMETER, "参数错误", "", 0);
    }

    /**
     * 当前请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("请求方法错误，请求URL: {}", request.getServletPath(), e);
        return new Result(StatusCode.FAILED_SECURE, "当前接口不支持" + request.getMethod() + "方法", "", 0);
    }

    /**
     * 文件上传超出限制异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception, HttpServletRequest request) {
        log.error("文件上传超出限制异常，请求URL: {}", request.getServletPath(), exception);
        return new Result(StatusCode.ELSE, "文件大小超出限制, 请压缩或降低文件大小", "", 0);
    }

    // 项目运行异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception exception, HttpServletRequest request) {
        log.error("服务器异常 Request URL: {}", request.getServletPath(), exception);
        // 注意不要将异常堆栈信息返回，会有安全问题
        return new Result(exception);
    }
}