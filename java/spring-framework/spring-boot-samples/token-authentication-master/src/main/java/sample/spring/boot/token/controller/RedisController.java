package sample.spring.boot.token.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/getstring/{key}")
    public String getString(@PathVariable("key") String key) {
        System.out.println(key);
        Boolean booleann = redisTemplate.delete("key");
        redisTemplate.boundValueOps("name").set("");
        ValueOperations<String, String> vops = redisTemplate.opsForValue();
        Long expire = redisTemplate.getExpire(key);
        return "";
    }
}
