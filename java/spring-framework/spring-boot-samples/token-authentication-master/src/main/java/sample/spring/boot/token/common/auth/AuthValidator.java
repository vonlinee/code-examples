package sample.spring.boot.token.common.auth;

import java.util.Map;

/**
 * 身份校验
 */
public interface AuthValidator {
    boolean validate(Map<String, Object> info);
}
