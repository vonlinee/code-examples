package io.devpl.auth.shiro;

import com.lancoo.supervisionplatform.config.ConfigProperties;
import com.lancoo.supervisionplatform.service.IIdentityService;
import org.apache.shiro.mgt.SubjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类：Shiro + JWT 代替默认的Cookie和Session
 * @author Xu Jiabao
 * @since 2022/3/16
 */
@Configuration
public class ShiroJwtConfig {

    @Autowired
    private ConfigProperties configProperties;

    /**
     * 手动创建Subject工厂，代替默认的DefaultWebSubjectFactory，关闭自动创建Session功能
     * @return SubjectFactory
     */
    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtWebSubjectFactory();
    }

    /**
     * 实际执行JWT认证和授权的类
     * @param identityService 身份Service，父类AbstractRealm使用
     * @return jwt域
     */
    @Bean
    public JwtRealm jwtRealm(AuthService identityService) {
        JwtRealm realm = new JwtRealm(identityService);
        realm.setAuthenticationTokenClass(JwtToken.class);
        realm.setCachingEnabled(false);
        return realm;
    }
}
