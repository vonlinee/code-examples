package code.example.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//通过Java配置类的方式配置Spring MVC的相关组件，要求Servlet必须3.0以上的（所以tomcat版本必须是7以上的）
//在Spring Mvc官方，建议我们在配置Java配置类的时候，对于Service、DAO相关的配置尽量配置在
//Root WebApplicationContext , 对于和前端视图做交互的配置在Servlet WebApplicationContext)
//https://blog.csdn.net/Hicodden/article/details/111412025
@EnableWebMvc
@ComponentScan(basePackages = "code.example.springmvc", includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
				Controller.class })}, useDefaultFilters = false)
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
}
//WebMvcConfig用于扫描@Controller注解