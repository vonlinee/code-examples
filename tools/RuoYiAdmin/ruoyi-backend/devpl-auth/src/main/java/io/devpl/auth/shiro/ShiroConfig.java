package io.devpl.auth.shiro;

import com.lancoo.supervisionplatform.config.ConfigProperties;
import com.lancoo.supervisionplatform.service.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro权限控制配置类
 * 开启AOP注解支持的两个类AuthorizationAttributeSourceAdvisor和DefaultAdvisorAutoProxyCreator已自动配置
 * 见 org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration
 * @author Xu Jiabao
 * @since 2022/2/9
 */
@Configuration
public class ShiroConfig {


    // 配置密码加密方式，登录与注册（新增）时的加密方式保持一致
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法: MD5，32位，不区分大小写
        hashedCredentialsMatcher.setHashAlgorithmName(ConfigProperties.ENCRYPT_ALGORITHM);
        // 散列的次数
        hashedCredentialsMatcher.setHashIterations(ConfigProperties.HASH_ITERATIONS);
        // storedCredentialsHexEncoded默认true，用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    // 用户名密码认证
    @Bean(value = "usernamePasswordRealm")
    public UsernamePasswordRealm passwordRealm(ILocalUserService localUserService, AuthService identityService,
                                               ISaltService saltService) {
        UsernamePasswordRealm realm = new UsernamePasswordRealm(localUserService, identityService, saltService);
        // 配置自定义的加密算法来验证密码
        // realm.setCredentialsMatcher(hashedCredentialsMatcher);
        // 配置支持验证的Token类型（默认是UsernamePasswordToken）
        realm.setAuthenticationTokenClass(MyUsernamePasswordToken.class);
        // 不需要缓存，只有登录时调用一次，后续校验JWT
        realm.setCachingEnabled(false);
        return realm;
    }

    // 手机号认证
    @Bean
    public PhoneCodeRealm phoneRealm(IShortMsgService codeService, ILocalUserService userService, AuthService identityService) {
        PhoneCodeRealm realm = new PhoneCodeRealm(codeService, userService, identityService);
        // 纯文本比对密码，不加密，默认即可，为 SimpleCredentialsMatcher
        // realm.setCredentialsMatcher(new SimpleCredentialsMatcher());
        realm.setAuthenticationTokenClass(PhoneCodeToken.class);
        // 不需要缓存，只有登录时调用一次，后续校验JWT
        realm.setCachingEnabled(false);
        return realm;
    }

    // 基础平台用户认证
    @Bean
    public RemoteSystemRealm remoteSystemRealm(IRemoteUserService remoteUserService, AuthService identityService) {
        RemoteSystemRealm realm = new RemoteSystemRealm(remoteUserService, identityService);
        // 纯文本比对密码，默认即可
        // realm.setCredentialsMatcher(new SimpleCredentialsMatcher());
        realm.setAuthenticationTokenClass(RemoteSystemToken.class);
        // 不需要缓存，只有登录时调用一次，后续校验JWT
        realm.setCachingEnabled(false);
        return realm;
    }

    // 权限管理器，配置Realm，多Realm认证，根据支持的Token类型进行选择
    @Bean
    public SessionsSecurityManager securityManager(UsernamePasswordRealm usernamePasswordRealm,
                                                   PhoneCodeRealm phoneCodeRealm,
                                                   RemoteSystemRealm remoteSystemRealm,
                                                   JwtRealm jwtRealm,
                                                   SubjectFactory subjectFactory) {
        // 认证和授权的类型
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(usernamePasswordRealm);
        realms.add(phoneCodeRealm);
        realms.add(remoteSystemRealm);
        realms.add(jwtRealm);

        // 关闭将Subject保存到Session中的功能
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(evaluator);

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realms);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 创建ShiroFilterFactoryBean，配置Shiro拦截器，设置放行和需要认证的路径
     * anon对应AnonymousFilter匿名过滤器，不需要认证和授权
     * authc对应FormAuthenticationFilter，需要身份认证，已用JwtFilter代替
     * jwt对应JwtFilter，验证请求头Authorization的jwt
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 配置拦截器名称和拦截器实例
        Map<String, Filter> filterMap = new HashMap<>();
        // name与filterChainDefinitionMap的value一致
        filterMap.put("jwt", new JwtFilter());

        // 配置哪些访问路径需要认证和授权。anon可以匿名访问，jwt使用JwtFilter校验
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/api/share/**", "anon");
        // 密码登录、短信登录、基础平台用户登录接口放行
        filterChainDefinitionMap.put("/api/user/pwd", "anon");
        filterChainDefinitionMap.put("/api/user/sms", "anon");
        filterChainDefinitionMap.put("/api/base/user", "anon");
        // 放行基础平台用户“在线状态”接口，放行本系统用户“在线状态”接口
        filterChainDefinitionMap.put("/api/base/user/state", "anon");
        filterChainDefinitionMap.put("/api/user/state", "anon");
        // 放行机构用户注册接口
        filterChainDefinitionMap.put("/api/user", "anon");
        // 放行省市区县接口
        filterChainDefinitionMap.put("/api/county/all", "anon");
        // 放行附件上传接口。因为公众新增投诉时需要上传图片。
        filterChainDefinitionMap.put("/api/attachment/single", "anon");
        // 放行获取私有附件接口，在Controller中校验param（即JWT），方便前端通过a、img标签获取图片
        filterChainDefinitionMap.put("/api/attachment", "anon");
        // 放行无权限和未登录的接口
        filterChainDefinitionMap.put("/api/user/notLoggedIn", "anon");
        filterChainDefinitionMap.put("/api/identity/unauthorized", "anon");
        // 静态资源：执行顺序Shiro Filter -> ResourceHttpRequestHandler（即WebMvcConfig中配置的ResourceLocations）
        // 注意静态资源的路径不需要ResourceLocations中配置的前缀
        filterChainDefinitionMap.put("*.js", "anon");
        filterChainDefinitionMap.put("*.css", "anon");
        filterChainDefinitionMap.put("*.html", "anon");
        filterChainDefinitionMap.put("/api/agencyPublicInfo", "anon");
        filterChainDefinitionMap.put("/api/agencyPublicInfo/**", "anon");
        filterChainDefinitionMap.put("/api/sms", "anon");
        //服务平台配置项
        filterChainDefinitionMap.put("/api/portalConfig", "anon");
        filterChainDefinitionMap.put("/api/town/**", "anon");
        filterChainDefinitionMap.put("/api/town/**/**", "anon");
        filterChainDefinitionMap.put("/api/town/**/**/**", "anon");
        filterChainDefinitionMap.put("/api/county/**", "anon");
        filterChainDefinitionMap.put("/api/city/**", "anon");
        filterChainDefinitionMap.put("/api/province/**", "anon");
        filterChainDefinitionMap.put("/api/agencyBasic/listBigData", "anon");
        // 剩余的都需要认证，JWTFilter的isAccessAllowed方法进行判断
        filterChainDefinitionMap.put("/**", "jwt");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 未登录时跳转URL，因为前后端分离，所以这里在下列接口中返回提示登录信息，这些接口同样要放行
        shiroFilterFactoryBean.setLoginUrl("/api/user/notLoggedIn");
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/identity/unauthorized");
        // 配置拦截路径与使用的过滤器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 配置过滤器
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

}
