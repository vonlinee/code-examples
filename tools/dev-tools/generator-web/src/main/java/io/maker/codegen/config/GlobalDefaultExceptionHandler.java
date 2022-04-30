package io.maker.codegen.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import io.maker.codegen.entity.ReturnT;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * 
 * 如果我们有想要拦截的异常类型，就新增一个方法，使用@ExceptionHandler注解修饰，注解参数为目标异常类型
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnT defaultExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return ReturnT.error("代码生成失败:"+e.getMessage());
    }
}
