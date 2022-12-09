package io.devpl.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 参考<a href="https://blog.csdn.net/qq_37771475/article/details/86153799">...</a>
 * <p>
 * 1.<a href="https://felord.cn/spring-security-dynamic-rbac-a.html">...</a>
 *
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SpringSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Component
    private static class SecurityConfigurer extends WebSecurityConfigurerAdapter {

        @Resource
        private PasswordEncoder pwdEncoder;

        // 配置user-detail（用户详细信息）服务
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // 基于内存来存储用户信息
            auth.inMemoryAuthentication().passwordEncoder(pwdEncoder).withUser("user")
                .password(pwdEncoder.encode("123")).roles("USER").and().withUser("admin")
                .password(pwdEncoder.encode("456")).roles("USER", "ADMIN");
        }

        // 配置Spring Security的Filter链
        @Override
        public void configure(WebSecurity web) throws Exception {

        }

        // 配置如何通过拦截器保护请求
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/", "home").permitAll().anyRequest().authenticated().and().formLogin()
                .loginPage("/login").permitAll().and().logout().permitAll();
        }
    }
}
