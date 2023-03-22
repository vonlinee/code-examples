package io.devpl.toolkit.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * 封装Controller层的返回值，可能会损耗一定的性能
 */
@RestControllerAdvice
public class HandlerMethodReturnValueWrapper implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(HandlerMethodReturnValueWrapper.class);

    /**
     * 是否支持给定的控制器方法返回类型和选定的HttpMessageConverter类型；若不支持则就不会对数据进行做统一处理
     *
     * @param returnType    返回类型
     * @param converterType 选择的转换器类型
     * @return 是否支持
     */
    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> declaringClass = returnType.getDeclaringClass();
        boolean isController = declaringClass.isAnnotationPresent(Controller.class) || declaringClass.isAnnotationPresent(RestController.class);
        if (!isController) {
            log.info("不是Controller {}", returnType.getDeclaringClass());
            return false;
        }
        Method method = returnType.getMethod();
        if (method == null) {
            return false;
        }
        if (Result.class.isAssignableFrom(method.getReturnType())) {
            // 若方法返回值已经被封装，则不进行拦截
            log.info("已被Result封装");
            return false;
        }
        return true;
    }

    /**
     * 在选择HttpMessageConverter之后以及在调用其write方法之前调用
     *
     * @param body                  你传入的数据
     * @param returnType            controller层方法返回的类型
     * @param selectedContentType   通过内容协商选择的内容类型
     * @param selectedConverterType 选择要写入响应的转换器类型
     * @param request               当前请求
     * @param response              当前响应
     * @return 传入的数据或修改的(可能是新的)实例
     */
    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body instanceof Result) {
            // 提供一定的灵活度，如果body已经被包装了，就不进行包装
            return body;
        }
        if (body instanceof String) {
            // 解决返回值为字符串时，不能正常包装
            return Results.of(body).toString();
        }
        return Results.of(body);
    }
}
