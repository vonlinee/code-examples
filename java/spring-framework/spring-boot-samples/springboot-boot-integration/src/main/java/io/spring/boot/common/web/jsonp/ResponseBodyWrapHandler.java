package io.spring.boot.common.web.jsonp;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * ResponseBody 处理类
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年2月29日
 */
public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {

    protected final Log logger = LogFactory.getLog(getClass());

    private final HandlerMethodReturnValueHandler delegate;

    private Set<String> jsonpParameterNames = new LinkedHashSet<String>(Arrays.asList("jsonp", "callback"));

    /**
     * Pattern for validating jsonp callback parameter values.
     */
    private static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_\\.]*");

    private String getJsonpParameterValue(NativeWebRequest request) {
        if (this.jsonpParameterNames != null) {
            for (String name : this.jsonpParameterNames) {
                String value = request.getParameter(name);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                if (!isValidJsonpQueryParam(value)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Ignoring invalid jsonp parameter value: " + value);
                    }
                    continue;
                }
                return value;
            }
        }
        return null;
    }

    protected boolean isValidJsonpQueryParam(String value) {
        return CALLBACK_PARAM_PATTERN.matcher(value).matches();
    }

    public ResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        String jsonpParameterValue = getJsonpParameterValue(webRequest);
        if (jsonpParameterValue != null) {
            if (!(returnValue instanceof MappingJacksonValue)) {
                MappingJacksonValue container = new MappingJacksonValue(returnValue);
                // container.setJsonpFunction(jsonpParameterValue);
                returnValue = container;
            }
        }
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}