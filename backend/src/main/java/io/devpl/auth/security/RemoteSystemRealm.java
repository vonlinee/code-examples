package io.devpl.auth.security;

import io.devpl.auth.domain.BaseUser;
import io.devpl.auth.domain.User;
import io.devpl.auth.service.IIdentityService;
import io.devpl.auth.service.IRemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;

import java.io.IOException;

/**
 * 自定义Realm，用于基础平台用户以Token方式登录，进入管理端前需要subject.login(token)
 * 重写doGetAuthenticationInfo、doGetAuthorizationInfo方法完成认证和授权
 * Bean在config.ShiroConfig中注册，设置支持验证的Token
 */
@Slf4j
public class RemoteSystemRealm extends AbstractRealm {

    private final IRemoteUserService remoteUserService;

    public RemoteSystemRealm(IRemoteUserService remoteUserService, IIdentityService identityService) {
        super(identityService);
        this.remoteUserService = remoteUserService;
    }

    /**
     * 认证，调用基础平台获得登录用户信息
     * @param authenticationToken RemoteSystemToken
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        try {
            // 检查用户是否在线（令牌是否有效），获取登录用户信息，转换成本系统的用户BaseUser -> User
            if (remoteUserService.checkOnline(token)) {
                final BaseUser userByToken = remoteUserService.findUserByToken(token);
                // 将其他平台的用户信息转换成本平台适配的用户信息
                User user = new User();
                // 登录成功后返回JWT
//                try {
//                    user.setIdentityList(identityService.listIdentityByUserId(user.getUserId(), token, null));
//                } catch (Exception exception) {
//                    log.error("本地系统用户身份信息获取异常", exception);
//                }
                // credentials和loginToken一致
                return new SimpleAuthenticationInfo(user, token, getName());
            } else {
                throw new IncorrectCredentialsException("登录令牌不正确");
            }
        } catch (IOException exception) {
            // 基础平台连接异常
            throw new AuthenticationException("基础平台连接异常", exception);
        }
    }

}
