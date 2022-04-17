package io.spring.boot.common.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * 动态数据源注册:启动动态数据源请在启动类中添加 @Import(DynamicDataSourceRegistrar.class)
 */
public class DynamicDataSourceRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegistrar.class);

    /**
     * 类型转换器
     */
    private ConversionService conversionService = new DefaultConversionService();

    /**
     * 数据源属性
     */
    private PropertyValues dataSourcePropertyValues;

    // 默认数据源
    private DataSource defaultDataSource;

    //
    private final Map<String, DataSource> customDataSources = new HashMap<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("dataSource", defaultDataSource);
        SwitchDbInvoke.addOptionalDataSourceId("dataSource");
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);
        SwitchDbInvoke.addOptionalDataSourceId(customDataSources.keySet());
        // 创建并注册DynamicDataSource这个Bean到容器
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dynamicDataSource", beanDefinition);
        
        logger.info("register dynamic datasource: {}", targetDataSources);
    }

    /**
     * 根据连接信息创建DataSource
     */
    private DataSource buildDataSource(Map<String, String> dataSourceInfoMap) {
        try {
            String type = dataSourceInfoMap.get("type");
            if (type == null || type.length() == 0) {
                type = DataSourceType.DruidPool.getClassName();// 默认DataSource
            }
            Class<?> dataSourceClassType = ClassUtils.forName(type, null);
            @SuppressWarnings("unchecked")
			Class<DataSource> dataSourceType = (Class<DataSource>) dataSourceClassType;
            String driverClassName = dataSourceInfoMap.get("driver-class-name");
            String url = dataSourceInfoMap.get("url");
            String username = dataSourceInfoMap.get("username");
            String password = dataSourceInfoMap.get("password");
            DataSourceBuilder<DataSource> factory = DataSourceBuilder.create()
                    .driverClassName(driverClassName)
                    .url(url)
                    .username(username)
                    .password(password)
                    .type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载多数据源配置
     */
    @Override
    public void setEnvironment(Environment env) {
        initDefaultDataSource(env);
        initCustomDataSources(env);
    }

    /**
     * 初始化主数据源
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, String> dsMap = new HashMap<>();
        dsMap.put("type", "");
        dsMap.put("driver-class-name", "com.mysql.jdbc.Driver");
        dsMap.put("url", "jdbc:mysql://localhost:3306/test?useSSL=false");
        dsMap.put("username", "root");
        dsMap.put("password", "123456");
        defaultDataSource = buildDataSource(dsMap);
    }

    /**
     * 初始化更多数据源
     */
    private void initCustomDataSources(Environment env) {
        Properties properties = extractEnvironment(env, "spring.datasource");
    }

    /**
     * RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
     * @param env    Environment
     * @param prefix prefix
     * @return Properties
     */
    private Properties extractEnvironment(Environment env, String prefix) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
        Binder binder = new Binder(sources);
        BindResult<Properties> bindResult = binder.bind(prefix, Properties.class);
        return bindResult.get();
    }
}
