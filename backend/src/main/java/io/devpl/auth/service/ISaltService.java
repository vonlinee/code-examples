package io.devpl.auth.service;

import io.devpl.auth.domain.SecretKey;

/**
 * 加密盐（Salt，一个随机数）Service接口
 * 添加盐防止重放攻击
 * @author Xu Jiabao
 * @since 2022/4/22
 */
public interface ISaltService {

    /**
     * 生成加密盐
     * @return 加密盐 + Token
     */
    SecretKey generate();

    /**
     * MD5数据摘要
     * @param token 找到加密盐的Token
     * @param content 待加密（摘要）的内容，一般是已经过一次哈希
     * @return MD5加密内容，token无效时返回null
     */
    String md5Digest(String token, String content);
}
