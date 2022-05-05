package sample.spring.boot.token.common.auth;

import java.util.Map;

public class SessionAuthValidator implements AuthValidator {
    @Override
    public boolean validate(Map<String, Object> info) {
        return false;
    }
}
