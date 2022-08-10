package io.maker.common.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

@Configuration
public class MyBatisPlusConfiguration {

	private static final String[] PAGINATION_NAMES = {
		"com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor",
		"com.github.pagehelper.PageHelper"
	};
	
	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public Object paginationInterceptor() {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Class<?> clazz = null;
			for (String pagination : PAGINATION_NAMES) {
				clazz = ClassUtils.forName(pagination, loader);
				if (clazz != null) {
					break;
				}
			}
			if (clazz == null) { // 使用自定义的分页插件
				return null;
			}
			return clazz.newInstance();
		} catch (ClassNotFoundException | LinkageError e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
