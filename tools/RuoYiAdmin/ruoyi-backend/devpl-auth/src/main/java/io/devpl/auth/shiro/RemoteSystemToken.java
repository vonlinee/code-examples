package io.devpl.auth.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @since 2022/2/23
 */
public class RemoteSystemToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    // 登录令牌
    private String loginToken;

    // Whether or not 'rememberMe' should be enabled for the corresponding login attempt; default is false
    private boolean rememberMe = false;

    // The location from where the login attempt occurs, or null if not known or explicitly omitted.
    private String host;

    public RemoteSystemToken()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        {}

    public RemoteSystemToken(String loginToken) {
        this(loginToken, false, null);
    }

    public RemoteSystemToken(String loginToken, boolean rememberMe, String host) {
        this.loginToken = loginToken;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    /*
    Principal和Credentials都是LoginToken
     */

    @Override
    public Object getPrincipal() {
        return getLoginToken();
    }

    @Override
    public Object getCredentials() {
        return getLoginToken();
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clear() {
        this.loginToken = null;
        this.host = null;
        this.rememberMe = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(getLoginToken());
        sb.append(", rememberMe=").append(rememberMe);
        if (host != null) {
            sb.append(" (").append(host).append(")");
        }
        return sb.toString();
    }

}
