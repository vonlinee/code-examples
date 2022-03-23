package sample.springboot.config.props;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class BeanConfiguration {

	@Bean(name = "gson")
	public Gson gson() {
		return new GsonBuilder().setDateFormat("HH:mm:ss").create();
	}

	@Bean(name = "fastjson")
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		converter.setFastJsonConfig(config);
		return converter;
	}
}
