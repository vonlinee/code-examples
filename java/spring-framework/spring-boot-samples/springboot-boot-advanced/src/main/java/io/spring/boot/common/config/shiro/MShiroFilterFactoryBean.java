package io.spring.boot.common.config.shiro;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

/**
 * 继承ShiroFilterFactoryBean处理拦截资源文件问题。
 */
//@Component 不能加此注解
// 官方文档中也有提到，FactoryBean一般不作为普通Bean注入
public class MShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    // 对ShiroFilter来说，需要直接忽略的请求
    private Set<String> ignoreExt;

    public MShiroFilterFactoryBean() {
        super();
        ignoreExt = new HashSet<>();
        ignoreExt.add(".jpg");
        ignoreExt.add(".png");
        ignoreExt.add(".gif");
        ignoreExt.add(".bmp");
        ignoreExt.add(".js");
        ignoreExt.add(".css");
    }

    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }
        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }
        FilterChainManager manager = createFilterChainManager();
        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);
        return new MSpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

//	@Override
//    public AbstractShiroFilter getObject() throws Exception {
//		return null;
//    }

    /**
     * 稍微改变了org.apache.shiro.spring.web.ShiroFilterFactoryBean.SpringShiroFilter这个实现
     */
    private final class MSpringShiroFilter extends AbstractShiroFilter {
        private MSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }

        @Override
        protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse,
                                        FilterChain chain) throws ServletException, IOException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String str = request.getRequestURI().toLowerCase();
            // 因为ShiroFilter默认拦截所有请求，而在每次请求里面都做了session的读取和更新访问时间等操作，这样在集群部署session共享的情况下，数量级的加大了处理量负载。
            // 所以我们这里将一些能忽略的请求忽略掉。
            // 当然如果你的集群系统使用了动静分离处理，静态资料的请求不会到Filter这个层面，便可以忽略。
            boolean flag = true;
            int idx = 0;
            if ((idx = str.indexOf(".")) > 0) {
                str = str.substring(idx);
                if (ignoreExt.contains(str.toLowerCase()))
                    flag = false;
            }
            if (flag) {
                super.doFilterInternal(servletRequest, servletResponse, chain);
            } else {
                chain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}