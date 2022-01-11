package code.example.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * 自定扫描的包，在项目的根（RootConfig）配置类中的时候，不扫描@Controller的注解。
 */
@ComponentScan(basePackages = "com.example.web", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) })
public class RootConfig {
	
}
