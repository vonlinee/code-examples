package org.springboot.sample.config.jsonp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.ServletPathExtensionContentNegotiationStrategy;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * 处理Spring默认加载好的类，在原有类上使用自定义类进行包装处理。
 *
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年2月29日
 */
@Configuration
public class ResponseBodyProcessor extends WebMvcConfigurerAdapter implements InitializingBean {

	@Autowired
	private RequestMappingHandlerAdapter adapter;

	@Autowired
	private ContentNegotiationManager manager;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(returnValueHandlers);
		decorateHandlers(handlers);
		adapter.setReturnValueHandlers(handlers);

		processContentNegotiationManager();
	}

	private void processContentNegotiationManager() {
		// 处理JSONP的响应ContentType
		List<ContentNegotiationStrategy> strategies = manager.getStrategies();
		for (int i = 0; i < manager.getStrategies().size(); i++) {
			if (manager.getStrategies().get(i) instanceof ServletPathExtensionContentNegotiationStrategy) {
				strategies.set(i, new ContentNegotiationStrategyWrap(manager.getStrategies().get(i)));
				manager = new ContentNegotiationManager(strategies);
				break;
			}
		}
	}

	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler instanceof RequestResponseBodyMethodProcessor) {
				// 用自己的ResponseBody包装类替换掉框架的，达到返回Result的效果
				ResponseBodyWrapHandler decorator = new ResponseBodyWrapHandler(handler);
				int index = handlers.indexOf(handler);
				handlers.set(index, decorator);
				break;
			}
		}
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (HttpMessageConverter<?> httpMessageConverter : converters) {
			// 为 MappingJackson2HttpMessageConverter 添加 "application/javascript"
			// 支持，用于响应JSONP的Content-Type
			if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter convert = (MappingJackson2HttpMessageConverter) httpMessageConverter;
				List<MediaType> medisTypeList = new ArrayList<>(convert.getSupportedMediaTypes());
				medisTypeList.add(MediaType.valueOf("application/javascript;charset=UTF-8"));
				convert.setSupportedMediaTypes(medisTypeList);
				break;
			}
		}
		super.extendMessageConverters(converters);
	}
}