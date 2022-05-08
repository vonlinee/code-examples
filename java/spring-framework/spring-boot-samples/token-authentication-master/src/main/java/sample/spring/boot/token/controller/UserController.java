package sample.spring.boot.token.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import sample.spring.boot.token.annotation.AuthToken;
import sample.spring.boot.token.mapper.UserMapper;
import sample.spring.boot.token.model.Result;
import sample.spring.boot.token.utils.ConstVal;
import sample.spring.boot.token.utils.Md5TokenGenerator;

/**
 * http://localhost:8888/user/login?username=zs&password=123
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private Md5TokenGenerator tokenGenerator;

	@Resource
	private UserMapper userMapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@GetMapping("/welcome")
	public String welcome() {
		return "UserController token authentication";
	}

	@GetMapping(value = "/login")
	public Result login(String username, String password) {
		// 先从Redis中获取token，如果不存在，则查寻数据库信息
		// 如果存在该用户信息，则生成token。并将用户信息存入redis
		// 不存在则提示用户名密码错误，返回错误信息给前端
		Map<String, Object> map = new HashMap<>();
		map.put("userName", username);
		map.put("password", password);
		Map<String, Object> user = userMapper.getUser(map);
		logger.info("user:{}", user);
		JSONObject result = new JSONObject();
		if (user != null) {
			String token = tokenGenerator.generate(username, password);
			redisTemplate.boundValueOps(username).set(token, ConstVal.TOKEN_EXPIRE_TIME);
			// 设置key生存时间，当key过期时，它会被自动删除，时间是秒
			redisTemplate.boundValueOps(token).set(username, ConstVal.TOKEN_EXPIRE_TIME);
			long currentTime = System.currentTimeMillis();
			redisTemplate.boundValueOps(token + username).set(String.valueOf(currentTime), ConstVal.TOKEN_EXPIRE_TIME);
			// 用完关闭
			result.put("status", "登录成功");
			result.put("token", token);
		} else {
			result.put("status", "登录失败");
			result.put("cause", String.format("用户名%s，密码%s", username, password));
			return Result.builder()
					.code(300)
					.message("登录失败")
					.data(result)
					.build();
		}
		return Result.builder()
				.code(200)
				.message("登录成功")
				.data(result)
				.build();
	}

	@AuthToken
	@GetMapping(value = "/test")
	public Result test() {
		logger.info("已进入test路径");
		return Result.builder()
				.code(200)
				.message("Success")
				.data("test url")
				.build();
	}
}
