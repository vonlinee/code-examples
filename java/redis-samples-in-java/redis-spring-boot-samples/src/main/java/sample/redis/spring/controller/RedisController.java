package sample.redis.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/set")
    @ResponseBody
    public Map<String, Object> set(@RequestBody Map<String, Object> param) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("url", "www.baidu.com");
        List<Object> list = ops.getOperations().exec();
        System.out.println(list);

        redisTemplate.multi();

        return param;
    }
}
