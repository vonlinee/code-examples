package code.example.springmvc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 继承关系如下：
 * WebApplicationInitializer
 * 			  |
 * AbstractContextLoaderInitializer
 * 			  |
 * AbstractDispatcherServletInitializer
 * 			  |
 * AbstractAnnotationConfigDispatcherServletInitializer
 * 
 * @author someone
 *
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 获取根容器的配置类：（Spring的配置文件），主要是配Service、DAO、事务等
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootConfig.class };
	}

	/**
	 * 获取web容器的配置类，主要是配置视图解析器、拦截器的
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	/**
	 * 返回我们需要拦截的请求
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
