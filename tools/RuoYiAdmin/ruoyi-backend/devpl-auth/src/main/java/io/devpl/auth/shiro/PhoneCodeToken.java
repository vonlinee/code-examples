package io.devpl.auth.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 手机号-验证码Token，类似于UsernamePasswordToken
 * 用于适应不同的Realm
 * @since 2022/2/22
 */
public class PhoneCodeToken implements AuthenticationToken {

    // 手机号
    private final String phone;
    // 验证码
    private String code;
    // 加密盐Key
    private final String token;


    public PhoneCodeToken(String phone, String code, String token) {
        this.phone = phone;
        this.code = code;
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return getPhone();
    }

    @Override
    public Object getCredentials() {
        return getCode();
    }

}
