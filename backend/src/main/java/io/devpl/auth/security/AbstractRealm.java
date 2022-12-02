package io.devpl.auth.security;

import io.devpl.auth.domain.User;
import io.devpl.auth.service.IIdentityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm，进行认证和授权。其中认证方式由子类实现（用户名密码或手机号验证码），授权方式相同
 * @author Xu Jiabao
 * @since 2022/2/23
 */
@Slf4j
public abstract class AbstractRealm extends AuthorizingRealm {

    protected final IIdentityService identityService;

    public AbstractRealm(IIdentityService identityService) {
        this.identityService = identityService;
    }

    /**
     * 授权。子类在重写认证方式时，要将User放入Principal中
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
        // 用户登录认证时已设置身份，如果没有再查询
        List<Identity> identityList = user.getIdentityList();
        if (identityList == null || identityList.isEmpty()) {
            try {
                // 本系统用户password为null，基础平台用户password为token
                identityList = identityService.listIdentityByUserId(user.getUserId(), user.getPassword(), null);
            } catch (Exception exception) {
                log.error("获取本地用户身份信息异常", exception);
            }
        }
         */
        // 使用UserType + UserClass 代替 Identity

        // 这里的getPrimaryPrincipal()获取的就是new SimpleAuthenticationInfo时放入的Principal
        User user = (User) principals.getPrimaryPrincipal();
        // 添加身份/角色
        Set<String> roleSet = new HashSet<>();
        // 添加权限
        // info.addStringPermission("permission");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleSet);
        return info;
    }
}
