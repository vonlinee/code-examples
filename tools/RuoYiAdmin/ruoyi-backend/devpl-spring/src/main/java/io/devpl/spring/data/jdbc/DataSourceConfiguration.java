package io.devpl.spring.data.jdbc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 数据源配置
 */
@Configuration(proxyBeanMethods = false)
@Import(DynamicDataSourceRegister.class)
public class DataSourceConfiguration {

}
