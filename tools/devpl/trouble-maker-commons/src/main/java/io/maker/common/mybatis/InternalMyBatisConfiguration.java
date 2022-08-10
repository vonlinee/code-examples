package io.maker.common.mybatis;

import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;

@Configuration
public class InternalMyBatisConfiguration {

	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setObjectWrapperFactory(new MapWrapperFactory());
	}

	public static class MapWrapperFactory implements ObjectWrapperFactory {
		@Override
		public boolean hasWrapperFor(Object object) {
			return  object instanceof Map;
		}

		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
			return new DefaultMapWrapper(metaObject, (Map) object);
		}
	}
}
