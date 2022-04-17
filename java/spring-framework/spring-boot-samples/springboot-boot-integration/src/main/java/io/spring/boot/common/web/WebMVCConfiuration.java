package io.spring.boot.common.web;

import io.spring.boot.common.web.servlet.MyServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.spring.boot.common.web.viewresolver.JsonViewResolver;
import io.spring.boot.common.web.viewresolver.PdfViewResolver;
import io.spring.boot.common.web.viewresolver.XlsViewResolver;
import io.spring.boot.common.web.interceptor.JsonErrorMsgInterceptor;
import io.spring.boot.common.web.interceptor.WebRquestTracker;
import io.spring.boot.common.web.interceptor.MyInterceptor2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMVCConfiuration implements EnvironmentAware, WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMVCConfiuration.class);

    // private RelaxedPropertyResolver propertyResolver;

    @Value("${spring.datasource.url}")
    private String myUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new WebRquestTracker()).addPathPatterns("/**");
        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
//		registry.addInterceptor(new JsonErrorMsgInterceptor()).addPathPatterns("*.json");
        registry.addInterceptor(new JsonErrorMsgInterceptor()).addPathPatterns("/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问myres根目录下的fengjing.jpg 的URL为 http://localhost:8080/myres/fengjing.jpg 不影响Spring Boot的默认的 /** 映射，可以同时使用。
        registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
        // 访问myres根目录下的fengjing.jpg 的URL为 http://localhost:8080/fengjing.jpg （/** 会覆盖系统默认的配置）
        // registry.addResourceHandler("/**").addResourceLocations("classpath:/myres/").addResourceLocations("classpath:/static/");
        // 可以直接使用addResourceLocations 指定磁盘绝对路径，同样可以配置多个位置，注意路径写法需要加上file:
        registry.addResourceHandler("/myimgs/**").addResourceLocations("file:H:/myimgs/");
    }

    /**
     * 这个方法只是测试实现EnvironmentAware接口，读取环境变量的方法
     */
    @Override
    public void setEnvironment(Environment env) {
        logger.info("加载自定义WebMVCConfiuration ==============> ");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            // 为 MappingJackson2HttpMessageConverter 添加 "application/javascript" 支持，用于响应JSONP的Content-Type
            if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter convert = (MappingJackson2HttpMessageConverter) httpMessageConverter;
                List<MediaType> medisTypeList = new ArrayList<>(convert.getSupportedMediaTypes());
                medisTypeList.add(MediaType.valueOf("application/javascript;charset=UTF-8"));
                convert.setSupportedMediaTypes(medisTypeList);
                break;
            }
        }
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.TEXT_HTML)
                // 启用
                // .useJaf(true)
                // .favorPathExtension(true)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("pdf", MediaType.valueOf("application/pdf"))
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xls", MediaType.valueOf("application/vnd.ms-excel"))
                .ignoreAcceptHeader(true);
    }

    /*
     * Configure ContentNegotiatingViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        // Define all possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<>();
        resolvers.add(new JsonViewResolver());
        resolvers.add(new PdfViewResolver());
        resolvers.add(new XlsViewResolver());
        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    /**
     * 修改DispatcherServlet默认配置
     */
    @Bean
    public ServletRegistrationBean<Servlet> dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<>(dispatcherServlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("*.do");
        registration.addUrlMappings("*.json");
        return registration;
    }

    /**
     * 使用代码注册Servlet（不需要@ServletComponentScan注解）
     */
    @Bean(name = "test")
    public ServletRegistrationBean<Servlet> servletRegistrationBean() {
        return new ServletRegistrationBean<>(new MyServlet(), "/xs/*");// ServletName默认值为首字母小写，即myServlet
    }

    // @Bean
    // public MethodValidationPostProcessor methodValidationPostProcessor() {
    //     return new MethodValidationPostProcessor();
    // }
}
