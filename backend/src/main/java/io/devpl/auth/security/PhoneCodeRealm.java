package io.devpl.auth.security;

import io.devpl.auth.domain.User;
import io.devpl.auth.service.IIdentityService;
import io.devpl.auth.service.ILocalUserService;
import io.devpl.auth.service.IShortMsgService;
import org.apache.shiro.authc.*;

/**
 * 自定义Realm，用于本系统用户以手机号-验证码方式登录
 * 重写doGetAuthenticationInfo、doGetAuthorizationInfo方法完成认证和授权
 * Bean在config.ShiroConfig中注册，设置支持验证的Token
 */
public class PhoneCodeRealm extends AbstractRealm {

    // 短信验证码服务
    private final IShortMsgService codeService;
    private final ILocalUserService userService;

    public PhoneCodeRealm(IShortMsgService codeService, ILocalUserService userService, IIdentityService identityService) {
        super(identityService);
        this.codeService = codeService;
        this.userService = userService;
    }

    /**
     * 短信认证
     * @param token 手机号-验证码Token
     * @return 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String phone = (String) token.getPrincipal();
        // 根据手机号找出对应的User，如果为空说明该手机号未注册
        User user = userService.findUserByPhone(phone);
        if (user == null) {
            throw new UnknownAccountException("手机号未注册");
        }
        user.setPassword(null);
        SimpleAuthenticationInfo info;
        // 短信验证码校验
        switch (codeService.checkCode(phone, (String) token.getCredentials(), ((PhoneCodeToken)token).getToken())) {
            case 0:
                throw new IncorrectCredentialsException("验证码不正确");
            case -1:
                throw new ExpiredCredentialsException("验证码已过期");
            case 1:
                // 验证码校验通过，直接硬编码Credentials，设置CredentialsMatcher不加密，直接比较
                ((PhoneCodeToken) token).setCode("123456");
                return new SimpleAuthenticationInfo(user, "123456", getName());
            default:
                return null;
        }
    }
}
