package sample.spring.boot.token.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import sample.spring.boot.token.annotation.AuthToken;
import sample.spring.boot.token.utils.ConstVal;
import sample.spring.boot.token.utils.RedisClient;

/**
 * 通过HandlerInterceptor校验token
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisClient redis;

	// 存放鉴权信息的Header名称，默认是Authorization
	private static final String httpHeaderName = "Authorization";

	// 鉴权失败后返回的错误信息，默认为401 unauthorized
	private String unauthorizedErrorMessage = "401 unauthorized";

	// 鉴权失败后返回的HTTP错误码，默认为401
	private static final int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

	/**
	 * 存放登录用户模型Key的Request Key
	 */
	public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		AuthToken tokenAnnotation = method.getAnnotation(AuthToken.class);
		if (tokenAnnotation == null) {
			return true;
		}
		tokenAnnotation = handlerMethod.getBeanType().getAnnotation(AuthToken.class);
		if (tokenAnnotation == null) {
			return true;
		}
		// 如果打上了AuthToken注解则需要验证token
		String token = request.getParameter(httpHeaderName);
		log.info("Get token from request is {} ", token);
		String username = "";
		if (token != null && token.length() != 0) {
			username = redis.getValue(token);
			log.info("Get username from Redis is {}", username);
		}
		if (username != null && !username.trim().equals("")) {
			long tokeBirthTime = Long.parseLong(redis.getValue(token + username));
			long diff = System.currentTimeMillis() - tokeBirthTime;
			log.info("token is exist : {} ms", diff);
			// 重新设置Redis中的token过期时间
			if (diff > ConstVal.TOKEN_RESET_TIME) {
				redis.setExpire(username, ConstVal.TOKEN_EXPIRE_TIME);
				redis.setExpire(token, ConstVal.TOKEN_EXPIRE_TIME);
				log.info("Reset expire time success!");
				long newBirthTime = System.currentTimeMillis();
				redis.set(token + username, Long.toString(newBirthTime));
			}
			request.setAttribute(REQUEST_CURRENT_KEY, username);
			return true;
		} else {
			JSONObject jsonObject = new JSONObject();
			PrintWriter out = null;
			try {
				response.setStatus(unauthorizedErrorCode);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				jsonObject.put("code", response.getStatus());
				jsonObject.put("message", HttpStatus.UNAUTHORIZED);
				out = response.getWriter();
				out.println(jsonObject);
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != out) {
					out.flush();
					out.close();
				}
			}
		}
		request.setAttribute(REQUEST_CURRENT_KEY, null);
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
