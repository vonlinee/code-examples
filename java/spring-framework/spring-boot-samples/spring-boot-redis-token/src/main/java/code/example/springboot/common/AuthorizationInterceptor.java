package code.example.springboot.common;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import code.example.springboot.annotation.AuthToken;
import lombok.extern.slf4j.Slf4j;

/**
 * 鉴权拦截器 使用token鉴权
 */
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisUtils redisUtils;

	// 存放鉴权信息的Header名称，默认是Authorization
	private String httpHeaderName = "Authorization";

	// 鉴权失败后返回的错误信息，默认是401 unauthorized
	private String unauthorizedErrorMessage = "401 unathorized";

	// 鉴权失败后返回的HTTP错误码，默认为401
	private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

	// 存放登录用户模型Key的Request key
	public static final String REQUEST_CURRENT_KEY = "REUQEST_CURRENT_KEY";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 如果有@AuthoToken注解则需要验证token:
		if (method.getAnnotation(AuthToken.class) != null
				|| handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
			// 1.获取请求头的token
			String token = request.getHeader(httpHeaderName);
			log.info("token is ()", token);
			String username = "";
			// 根据token获取redis缓存的username
			if (token != null && token.length() != 0) {// 用token校验
				username = redisUtils.get(token).toString();// 根据token获取redis缓存中的username
				log.info("username is: {}", username);
				// 2.根据token+username获取token的初始时间
				if (username != null && !username.trim().equals("")) {// 校验成功
					Long tokenBirthTime = Long.valueOf((String) redisUtils.get(token + username));// 获取token初始时间
					log.info("token Birth time is: {}", tokenBirthTime);
					Long diff = System.currentTimeMillis() - tokenBirthTime;// 时间差——token存在了多久
					log.info("token is exist: {}", diff);
					// 3.重置缓存失效时间
					if (diff > ConstantKit.TOKEN_RESET_TIME) {
						redisUtils.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
						redisUtils.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
						log.info("Reset expire time success!");
						Long newBirthTime = System.currentTimeMillis();
						redisUtils.set(token + username, newBirthTime.toString());// 重置token初始时间
					}
					// 4/设置请求头
					request.setAttribute(REQUEST_CURRENT_KEY, username);
					return true;
				}
			} else {// 验证失败要重新登录
				response.sendRedirect("/studentinfo");
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
