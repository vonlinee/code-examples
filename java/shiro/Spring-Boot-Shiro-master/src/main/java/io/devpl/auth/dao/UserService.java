package io.devpl.auth.dao;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserService implements IUserService {

    @Override
    public UserInfo findByUserName(String username) {
        if (! DataSource.getData().containsKey(username))
            return null;
        UserInfo user = new UserInfo();
        Map<String, String> detail = DataSource.getData().get(username);
        user.setUsername(username);
        user.setPassword(detail.get("password"));
        user.setRole(detail.get("role"));
        user.setPermission(detail.get("permission"));
        return user;
    }
}
