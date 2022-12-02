package io.devpl.auth.security;

import io.devpl.auth.domain.User;
import io.devpl.auth.service.IIdentityService;
import io.devpl.auth.service.ILocalUserService;
import io.devpl.auth.service.ISaltService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;

/**
 * 自定义Realm，用于本系统用户以用户名和密码方式登录
 * 重写doGetAuthenticationInfo、doGetAuthorizationInfo方法完成认证和授权
 * Bean在config.ShiroConfig中注册，设置支持验证的Token
 */
@Slf4j
public class UsernamePasswordRealm extends AbstractRealm {

    private final ILocalUserService userService;
    private final ISaltService saltService;

    public UsernamePasswordRealm(ILocalUserService userService, IIdentityService identityService,
                                 ISaltService saltService) {
        super(identityService);
        this.userService = userService;
        this.saltService = saltService;
    }

    /**
     * 认证。用户名密码方式认证
     * @param token AuthenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 查询数据库，获取用户名和密码。数据库中存储的密码需要经过相同的加密处理。
        User user = userService.findUserByUsername((String) token.getPrincipal(), true);
        if (user == null)
            // 返回null，默认抛出UnknownAccountException
            return null;
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) token;
        String password = saltService.md5Digest(myToken.getToken(), user.getPassword());
        // 密码设置为null，防止subject.getPrincipal取出之后返回JSON
        user.setPassword(null);

        // 登录成功后，返回的是JWT
        // username不作为验证条件，可以将user放入，方便取出userId等信息
        return new SimpleAuthenticationInfo(user, password, getName());
    }

}
