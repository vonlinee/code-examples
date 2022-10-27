package code.example.springsecurity.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VerifyCodeFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if ("POST".equalsIgnoreCase(request.getMethod()) && "/doLogin".equals(request.getServletPath())) {
			// 验证码验证
			String requestCaptcha = request.getParameter("code");
			String genCaptcha = (String) request.getSession().getAttribute("index_code");
			if (!StringUtils.hasText(requestCaptcha))
				throw new AuthenticationServiceException("验证码不能为空!");
			if (!genCaptcha.equalsIgnoreCase(requestCaptcha)) {
				throw new AuthenticationServiceException("验证码错误!");
			}
		}
		chain.doFilter(request, response);
	}
}
