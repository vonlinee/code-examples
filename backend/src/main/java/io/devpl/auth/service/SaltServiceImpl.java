package io.devpl.auth.service;

import cn.hutool.core.util.RandomUtil;
import io.devpl.auth.domain.SecretKey;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 加密解密Service
 */
@Service
public class SaltServiceImpl implements ISaltService {

    // private final Cache cache = CacheManager.create().getCache("captcha");

    /**
     * 生成加密盐，一个10位随机数
     * @return 密钥 + Token
     */
    public SecretKey generate() {
        SecretKey secretKey = new SecretKey();
        secretKey.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        secretKey.setKey(RandomUtil.randomNumbers(10));
        // cache.put(new Element(secretKey.getToken(), secretKey.getKey()));
        return secretKey;
    }

    /**
     * MD5数据摘要
     * @param token   找到加密盐的Token
     * @param content 待加密（摘要）的内容，一般是已经过一次哈希
     * @return MD5加密内容，token无效时返回null
     */
    public String md5Digest(String token, String content) {
        // Element element = cache.get(token);
        // if (element == null || element.getObjectValue() == null) return null;
        // content = ((String) element.getObjectValue()) + content;
        // // 删除保存的加密盐，加密盐只使用一次
        // cache.remove(token);
        return DigestUtils.md5DigestAsHex(content.getBytes(StandardCharsets.UTF_8));
    }
}
