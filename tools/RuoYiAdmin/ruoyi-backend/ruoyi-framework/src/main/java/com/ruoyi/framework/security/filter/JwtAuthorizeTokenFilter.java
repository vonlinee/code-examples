package com.ruoyi.framework.security.filter;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.web.service.TokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * token过滤器 验证token有效性
 * <p>
 * 授权与认证分开
 * 授权：用户是否有操作本系统的权限
 * 认证：知道用户的身份
 * @author ruoyi
 */
@Component
public class JwtAuthorizeTokenFilter extends OncePerRequestFilter {

    @Resource
    private TokenService tokenService;

    /**
     * 从Redis中校验token并获取用户信息，放入Spring Security的管理中心
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException {
        // 暂时放开权限校验
        if (request.getParameter("skipAuth") != null) {
            logger.info("放开token校验 " + request.getRequestURI());

            SysUser sysUser = new SysUser();
            LoginUser loginUser = new LoginUser(sysUser, Set.of("*:*:*"));

            loginUser.setPermissions(Set.of("*:*:*"));
            loginUser.setToken(UUID.randomUUID().toString());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // chain.doFilter(request, response);
            return;
        }

        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null && SecurityUtils.getAuthentication() == null) {
            // 校验token有效性
            tokenService.verifyTokenExpiration(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
