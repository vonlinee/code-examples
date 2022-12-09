package io.devpl.auth.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 使用JWT代替原JSESSIONID的Cookie，解决Shiro默认返回的Cookie设置了http-only，脚本无法读取的问题
 * 本过滤器负责创建JwtToken，
 */
@Slf4j
public class JwtFilter extends FormAuthenticationFilter {

    /**
     * 判断访问是否被允许，JWT有效则允许访问
     * 由subject.login去验证，让SecurityUtils.getSubject可以获取用户
     * 登录成功后后端返回Token，此后所有需要认证的请求都需要在请求头上附带Token
     * Authorization={令牌值}
     * @param request     请求
     * @param response    响应
     * @param mappedValue the filter-specific config value mapped to this filter in the URL rules mappings.
     * @return true if request should be allowed access
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String authorization = ((HttpServletRequest) request).getHeader("Authorization");
        if (authorization == null) return false;
        // createToken
        JwtToken token = new JwtToken(authorization);
        Subject subject = getSubject(request, response);
        try {
            subject.login(token);
            // 不抛出异常则校验通过，返回true，允许访问
            return true;
        } catch (AuthenticationException exception) {
            return false;
        }
    }

    /**
     * 拒绝访问时，需要执行的操作
     * @param request  请求
     * @param response 响应
     * @return true if the request should continue to be processed;
     * false if the subclass will handle/render the response directly.
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 重写父类方法，不保存Request，否则报DisabledSessionException异常，因为已关闭Session创建功能
        // saveRequestAndRedirectToLogin(request, response);

        // 重定向到登录页面
        redirectToLogin(request, response);
        return false;
    }
}
