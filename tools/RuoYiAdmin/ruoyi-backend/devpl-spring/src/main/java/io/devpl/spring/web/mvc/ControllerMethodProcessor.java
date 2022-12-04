package io.devpl.spring.web.mvc;

import io.devpl.sdk.beans.impl.map.BeanMap;
import io.devpl.sdk.rest.RestfulResultTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import java.util.List;

/**
 * 如果使用@RestController会被其他返回值解析器处理
 * 处理Controller的方法参数和返回值
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
 */
@Slf4j
public class ControllerMethodProcessor extends AbstractMessageConverterMethodProcessor implements Ordered {

    public ControllerMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    /**
     * BeanMap继承自Map，会被Spring内置的Map类型的参数解析器处理
     * @param parameter the method parameter to check
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter => {}", parameter);
        return parameter.getParameterType() == BeanMap.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument => {}", parameter);
        return new BeanMap();
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return RestfulResultTemplate.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        ServletServerHttpRequest inputMessage = createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);
        // Try even with null return value. ResponseBodyAdvice could get involved.
        writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
