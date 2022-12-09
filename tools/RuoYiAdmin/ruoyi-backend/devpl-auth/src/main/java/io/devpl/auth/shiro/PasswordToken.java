package io.devpl.auth.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 简单的基于用户名密码认证的Token
 */
public class PasswordToken implements AuthenticationToken {

    // 用户名
    private final String username;
    // 密码
    private final String password;
    // 加密盐Key
    private final String token;

    public PasswordToken(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}
