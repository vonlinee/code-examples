package io.devpl.auth.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.env.DefaultEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroDemo {

    private static final Logger log = LoggerFactory.getLogger(ShiroDemo.class);

    public static void test1() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        securityManager.setRealm(iniRealm);

        // 构造用户信息
        UsernamePasswordToken token = new UsernamePasswordToken("username", "password");
        // 获取当前用户信息
        Subject subject = SecurityUtils.getSubject();
        // 如果获取不到用户名就是登录失败，但登录失败的话，会直接抛出异常
        try {
            // getAuthenticationInfo 执行时机
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 重点！！！！！！
        // getAuthorizationInfo  执行时机 -- subject.hasRole()
        if (subject.hasRole("admin")) {

        }
        // return "/login";
    }

    public static void main(String[] args) {

        // 1.创建SecurityManagerFactory
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2.获取SecurityManager,绑定到SecurityUtils中
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 3.获取一个用户识别信息
        Subject currentUser = SecurityUtils.getSubject();
        // 4.判断是否已经身份验证
        if (!currentUser.isAuthenticated()) {
            // 4.1把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken("guest", "guest");
            // 4.2设置rememberme
            token.setRememberMe(true);
            try {
                // 4.3登录.
                currentUser.login(token);
            } catch (UnknownAccountException uae) { // 用户不存在异常
                log.info("****---->用户名不存在： " + token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException ice) {// 密码不匹配异常
                log.info("****---->" + token.getPrincipal() + " 的密码错误!");
                return;
            } catch (LockedAccountException lae) {// 用户被锁定
                log.info("****---->用户 " + token.getPrincipal() + " 已被锁定");
            } catch (AuthenticationException ae) { // 其他异常，认证异常的父类
                log.info("****---->用户" + token.getPrincipal() + " 验证发生异常");
            }
        }

        // 5.权限测试：
        // 5.1判断用户是否有某个角色
        if (currentUser.hasRole("guest")) {
            log.info("****---->用户拥有角色guest!");
        } else {
            log.info("****---->用户没有拥有角色guest");
            return;
        }
        // 5.2判断用户是否执行某个操作的权限
        if (currentUser.isPermitted("see")) {
            log.info("****----> 用户拥有执行此功能的权限");
        } else {
            log.info("****---->用户没有拥有执行此功能的权限");
        }

        // 6.退出
        System.out.println("****---->" + currentUser.isAuthenticated());
        currentUser.logout();
        System.out.println("****---->" + currentUser.isAuthenticated());

    }
}