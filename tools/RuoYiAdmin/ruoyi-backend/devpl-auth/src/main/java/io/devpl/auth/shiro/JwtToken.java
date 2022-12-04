package io.devpl.auth.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Xu Jiabao
 * @since 2022/3/18
 */
public class JwtToken implements AuthenticationToken {

    // jwt字符串
    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}