package com.ruoyi.framework.security.handle;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * <p>
 * 认证失败处理类 返回未授权
 * 被ExceptionTranslationFilter用来作为认证方案的入口，即当用户请求处理过程中遇见认证异常时，
 * 被异常处理器（ExceptionTranslationFilter）用来开启特定的认证流程。
 * </p>
 * AuthenticationEntryPoint 实现类，可以修改响应头属性信息或开启新的认证流程
 * @author ruoyi
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    /**
     * @param request  遇到了认证异常的用户请求
     * @param response 将要返回给用户的响应
     * @param e        请求过程中遇见的认证异常
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED, msg)));
        // 调到登录页面
    }
}
