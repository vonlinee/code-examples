package code.example.springboot.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * 跟踪一次web请求，此过滤器的优先级应比较高
 */
public class HttpTracerFilter extends OncePerRequestFilter {

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(HttpTracerFilter.class);

    private boolean wrapped;

    String contentType = "application/json;charset=UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 使用ContentCachingRequestWrapper缓存请求信息
        if (!wrapped) {
            if (!(request instanceof ContentCachingRequestWrapper)) {
                request = new ContentCachingRequestWrapper(request);
            }
            // 使用ContentCachingResponseWrapper缓存响应信息
            if (!(response instanceof ContentCachingResponseWrapper)) {
                response = new ContentCachingResponseWrapper(response);
            }
            wrapped = true;
        }
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            HttpTraceLog log = new HttpTraceLog(); //打印请求的详细信息

            StringBuffer requestURL = request.getRequestURL();
            boolean f = true;
            if (requestURL.indexOf("getUserIsOnline") < 0 && requestURL.indexOf("getAdminInfos") < 0 && requestURL.indexOf("getSystemAddressMsg") < 0 && requestURL.indexOf("druid") < 0) {
                log.setURL(request.getRequestURL());
                log.setMethod(request.getMethod());
                log.setParameterMap(JSON.toJSONString(request.getParameterMap()));
                log.setStartTime(dateformat.format(startTime));
                // 如果是POST请求，记录其请求体
                if ("POST".equals(request.getMethod())) {
                    log.setRequestBody(getRequestBody(request));
                } else {
                    log.setRequestBody("{}");
                }
                if (contentType.equals(response.getContentType())) {
                    log.setResponseBody(getResponseBody(response));
                }
            }
            //
            updateResponse(response);

            logger.info(String.valueOf(log));
        }
    }

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            //requestBody = JSON.parseObject(param, Map.class).toString();
        }
        return requestBody;
    }

    private String getResponseBody(HttpServletResponse response) {
        String responseBody = "";
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            responseBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
        }
        return responseBody;
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (responseWrapper != null) {
            responseWrapper.copyBodyToResponse();
        } else {
            // 无响应,比如404等
        }
    }
}
