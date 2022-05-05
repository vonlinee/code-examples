package code.example.springboot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * token生成器
 * @author jinv
 */
@Component
public class Md5TokenGenerator implements TokenGenerator {
	
    @Autowired
    RedisUtils redisUtils;

    /**
     * token生成器
     * @param strings 参数可以多个
     * @return token token->(s1+s2...+时间戳)MD5加密
     */
    @Override
    public String generate(String... strings) {
        long timeStamp = System.currentTimeMillis();//获取当前时间戳
        StringBuilder tokenMeta = new StringBuilder();
        for (String s: strings){//整合所有字符串参数为一个
            tokenMeta.append(s);
        }
        tokenMeta.append(timeStamp);//加入时间戳
        String token = DigestUtils.md5DigestAsHex(tokenMeta.toString().getBytes());//MD5加密
        return token;
    }

    /**
     * token存入redis缓存
     * @param username  用户名
     * @param password  密码
     * @return token(username+password+时间戳)MD5加密
     */
    public String tokenUtils(String username, String password){
        //设置token
        String token = generate(username,password);
        redisUtils.set(username,token, ConstantKit.TOKEN_EXPIRE_TIME);//五分钟失效缓存
        redisUtils.set(token,username,ConstantKit.TOKEN_EXPIRE_TIME);
        Long currentTime = System.currentTimeMillis();//获取当前时间戳
        redisUtils.set(username+token,currentTime);//设置第一次登陆的时间为token的初始时间
        return token;
    }
}
