package io.devpl.spring.web.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.*;

/**
 * 解析RequestParam注解标注的Map类型的方法参数，并且该注解未指定参数的名字
 *
 * <p>The created {@link Map} contains all request parameter name/value pairs,
 * or all multipart files for a given parameter name if specifically declared
 * with {@link MultipartFile} as the value type. If the method parameter type is
 * {@link MultiValueMap} instead, the created map contains all request parameters
 * and all their values for cases where request parameters have multiple values
 * (or multiple multipart files of the same name).
 * @see RequestParamMethodArgumentResolver
 * @see HttpServletRequest#getParameterMap()
 * @see MultipartRequest#getMultiFileMap()
 * @see MultipartRequest#getFileMap()
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
 * @see RequestParamMapMethodArgumentResolver  根据这个实现
 * @since 3.1
 */
public class RequestInfoMethodArgumentResolver implements HandlerMethodArgumentResolver, Ordered {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
        boolean f = (requestParam != null && Map.class.isAssignableFrom(parameter.getParameterType()) &&
                !StringUtils.hasText(requestParam.name()));
        return f || parameter.getParameterType() == RequestInfo.class;
    }

    /**
     * 具体实现参考：org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) throws Exception {
        // 可解析的类型
        ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
        // 参数类型是MultiValueMap的子类型
        if (MultiValueMap.class.isAssignableFrom(parameter.getParameterType())) {
            // 获取Value的泛型类型
            Class<?> valueType = resolvableType.as(MultiValueMap.class).getGeneric(1).resolve();
            // 文件
            if (valueType == MultipartFile.class) {
                MultipartRequest multipartRequest = MultipartResolutionDelegate.resolveMultipartRequest(webRequest);
                return (multipartRequest != null ? multipartRequest.getMultiFileMap() : new LinkedMultiValueMap<>(0));
            } else if (valueType == Part.class) {
                HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
                if (servletRequest != null && MultipartResolutionDelegate.isMultipartRequest(servletRequest)) {
                    Collection<Part> parts = servletRequest.getParts();
                    LinkedMultiValueMap<String, Part> result = new LinkedMultiValueMap<>(parts.size());
                    for (Part part : parts) {
                        result.add(part.getName(), part);
                    }
                    return result;
                }
                return new LinkedMultiValueMap<>(0);
            } else {
                // 普通的MultiValueMap类型
                Map<String, String[]> parameterMap = webRequest.getParameterMap();
                MultiValueMap<String, String> result = new LinkedMultiValueMap<>(parameterMap.size());
                parameterMap.forEach((key, values) -> {
                    for (String value : values) {
                        result.add(key, value);
                    }
                });
                return result;
            }
        } else {
            // 常规的Map类型
            Class<?> valueType = resolvableType.asMap().getGeneric(1).resolve();
            if (valueType == MultipartFile.class) {
                MultipartRequest multipartRequest = MultipartResolutionDelegate.resolveMultipartRequest(webRequest);
                return (multipartRequest != null ? multipartRequest.getFileMap() : new LinkedHashMap<>(0));
            } else if (valueType == Part.class) {
                HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
                if (servletRequest != null && MultipartResolutionDelegate.isMultipartRequest(servletRequest)) {
                    Collection<Part> parts = servletRequest.getParts();
                    LinkedHashMap<String, Part> result = CollectionUtils.newLinkedHashMap(parts.size());
                    for (Part part : parts) {
                        if (!result.containsKey(part.getName())) {
                            result.put(part.getName(), part);
                        }
                    }
                    return result;
                }
                return new LinkedHashMap<>(0);
            } else {
                RequestInfo requestInfo = new RequestInfo();
                requestInfo.setRequestParam(webRequest.getParameterMap());
                // 获取请求头
                HttpServletRequest nativeReq = webRequest.getNativeRequest(HttpServletRequest.class);
                requestInfo.setHeaders(getHttpHeaders(nativeReq, webRequest));
                if (nativeReq != null) {
                    requestInfo.setMethod(HttpMethod.resolve(nativeReq.getMethod()));
                    requestInfo.setPath(nativeReq.getRequestURI());
                }
                return requestInfo;
            }
        }
    }

    private HttpHeaders getHttpHeaders(HttpServletRequest nativeRequest, NativeWebRequest webRequest) {
        HttpHeaders httpHeaders = HttpHeaders.EMPTY;
        if (nativeRequest != null) {
            httpHeaders = new HttpHeaders();
            Enumeration<String> headerNames = nativeRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                httpHeaders.addAll(name, listOfEnumrations(nativeRequest.getHeaders(name)));
            }
            return httpHeaders;
        }
        if (webRequest == null) {
            return httpHeaders;
        }
        httpHeaders = new HttpHeaders();
        Iterator<String> headerNames = webRequest.getHeaderNames();
        while (headerNames.hasNext()) {
            String name = headerNames.next();
            String[] headerValues = webRequest.getHeaderValues(name);
            httpHeaders.addAll(name, Arrays.asList(headerValues == null ? new String[0] : headerValues));
        }
        return httpHeaders;
    }

    private List<String> listOfEnumrations(Enumeration<String> enumeration) {
        List<String> list = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
