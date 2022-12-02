package io.devpl.auth.security;

import org.apache.shiro.authc.AuthenticationToken;

public class MyUsernamePasswordToken implements AuthenticationToken {

    // 用户名
    private String username;
    // 密码
    private String password;
    // 加密盐Key
    private String token;

    public MyUsernamePasswordToken() {

    }

    public MyUsernamePasswordToken(String username, String password, String token) {
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
