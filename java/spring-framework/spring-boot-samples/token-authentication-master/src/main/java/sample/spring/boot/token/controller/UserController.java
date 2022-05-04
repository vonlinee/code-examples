package sample.spring.boot.token.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import sample.spring.boot.token.annotation.AuthToken;
import sample.spring.boot.token.mapper.UserMapper;
import sample.spring.boot.token.model.ResponseTemplate;
import sample.spring.boot.token.utils.ConstVal;
import sample.spring.boot.token.utils.Md5TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private Md5TokenGenerator tokenGenerator;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/welcome")
    public String welcome() {
        return "UserController token authentication";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseTemplate login(String username, String password) {
        logger.info("username:" + username + " password:" + password);

        // 先从Redis中获取token，如果不存在，则查寻数据库信息

        // 如果存在该用户信息，则生成token。并将用户信息存入redis

        // 不存在则提示用户名密码错误，返回错误信息给前端

        Map<String, Object> map = new HashMap<>();
        map.put("userName", username);
        Map<String, Object> user = userMapper.getUser(map);
        logger.info("user:" + user);
        JSONObject result = new JSONObject();
        if (user != null) {
            Jedis jedis = new Jedis("localhost", 6379);
            String token = tokenGenerator.generate(username, password);
            jedis.set(username, token);
            //设置key生存时间，当key过期时，它会被自动删除，时间是秒
            jedis.expire(username, ConstVal.TOKEN_EXPIRE_TIME);
            jedis.set(token, username);
            jedis.expire(token, ConstVal.TOKEN_EXPIRE_TIME);
            long currentTime = System.currentTimeMillis();
            jedis.set(token + username, String.valueOf(currentTime));
            //用完关闭
            jedis.close();
            result.put("status", "登录成功");
            result.put("token", token);
        } else {
            result.put("status", "登录失败");
        }
        return ResponseTemplate.builder()
                               .code(200)
                               .message("登录成功")
                               .data(result)
                               .build();
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @AuthToken
    public ResponseTemplate test() {
        logger.info("已进入test路径");
        return ResponseTemplate.builder()
                               .code(200)
                               .message("Success")
                               .data("test url")
                               .build();
    }
}
