package sample.spring.boot.token.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;


@Component
public class Md5TokenGenerator implements TokenGenerator {

    @Override
    public String generate(String... strings) {
        long timestamp = System.currentTimeMillis();
        String tokenMeta = "";
        for (String s : strings) {
            tokenMeta = tokenMeta + s;
        }
        tokenMeta = tokenMeta + timestamp;
        return DigestUtils.md5DigestAsHex(tokenMeta.getBytes());
    }
}
