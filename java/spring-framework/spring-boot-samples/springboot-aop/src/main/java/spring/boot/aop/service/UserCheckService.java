package spring.boot.aop.service;

import org.springframework.stereotype.Component;

@Component
public class UserCheckService {

    public Object checkUserInfo(String username, String password, boolean flag) {
        if ("zs".equals(username) && flag) {
            throw new IllegalArgumentException(String.join(username, password));
        }
        return password;
    }
}
