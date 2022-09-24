package io.devpl.spring.data.jdbc;

import io.devpl.spring.Devpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * 动态数据源注册<>br/>
 * 启动动态数据源请在启动类中（如SpringBootSampleApplication）
 * 添加 @Import(DynamicDataSourceRegister.class)
 *
 * @author http://blog.csdn.net/catoop/
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    private PropertyValues dataSourcePropertyValues;

    // 如配置文件中未指定数据源类型，使用该默认值
    private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
    // private static final Object DATASOURCE_TYPE_DEFAULT =
    // "com.zaxxer.hikari.HikariDataSource";

    // 数据源
    private DataSource defaultDataSource;
    private Map<String, DataSource> customDataSources = new HashMap<>();

    /**
     * 数据源配置前缀, 与其他部分共同组合成配置属性的名称，例如:
     * devpl.${prefix}.${name}.url
     * devpl.${prefix}.${name}.username
     */
    @Value("${devpl.datasource.config.prefix:datasource}")
    private String prefix = "datasource";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (importingClassMetadata != null) return;
        Map<Object, Object> targetDataSources = new HashMap<>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("dataSource", defaultDataSource);
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);
        for (String key : customDataSources.keySet()) {

        }
        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);
    }

    /**
     * 创建DataSource
     *
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null) {
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
            }
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder<? extends DataSource> factory = DataSourceBuilder.create()
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
     * 加载多数据源配置，手动进行绑定
     * https://www.jianshu.com/p/4feab6df384e
     */
    @Override
    public void setEnvironment(Environment env) {
        if (env instanceof AbstractEnvironment) {
            AbstractEnvironment environment = ((AbstractEnvironment) env);
            MutablePropertySources sources = environment.getPropertySources();
            for (PropertySource<?> source : sources) {
                // TODO 需要确保PropertySource的名称以devpl开头
                if (StringUtils.startsWithIgnoreCase(source.getName(), Devpl.NAME)) {
                    Map<String, Object> map = transfer(source);
                    // 配置的key
                    List<String> dsConfigKeys = new ArrayList<>();

                    final String prefix = "devpl.datasource.";
                    int len = prefix.length();

                    map.forEach((k, v) -> {
                        if (k.length() > len && k.startsWith(prefix)) {
                            k = k.substring(len);
                            int i = k.indexOf(".");
                            dsConfigKeys.add(k);
                        }
                    });
                    // 过滤包含数据库配置信息的key
                    // 符合要求的格式 ${name}.
                }
            }
        }
    }

    /**
     * 将PropertySource中所有配置转移到Map中保存
     *
     * @param propertySource
     * @return
     */
    private Map<String, Object> transfer(PropertySource<?> propertySource) {
        Map<String, Object> map = new HashMap<>();
        Object source = propertySource.getSource();
        if (source instanceof Map) {
            Set<? extends Map.Entry<?, ?>> entries = ((Map<?, ?>) source).entrySet();
            for (Map.Entry<?, ?> entry : entries) {
                Object key = entry.getKey();
                map.put((String) key, entry.getValue());
            }
        }
        return map;
    }

    /**
     * 初始化主数据源
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        // RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        // Map<String, Object> dsMap = new HashMap<>();
        // dsMap.put("type", propertyResolver.getProperty("type"));
        // dsMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
        // dsMap.put("url", propertyResolver.getProperty("url"));
        // dsMap.put("username", propertyResolver.getProperty("username"));
        // dsMap.put("password", propertyResolver.getProperty("password"));

        // defaultDataSource = buildDataSource(dsMap);
        dataBinder(defaultDataSource, env);
    }

    /**
     * 为DataSource绑定更多数据
     *
     * @param dataSource
     * @param env
     * @author SHANHY
     * @create 2016年1月25日
     */
    private void dataBinder(DataSource dataSource, Environment env) {
        // RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        // //dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
        // dataBinder.setConversionService(conversionService);
        // dataBinder.setIgnoreNestedProperties(false);//false
        // dataBinder.setIgnoreInvalidFields(false);//false
        // dataBinder.setIgnoreUnknownFields(true);//true
        // if (dataSourcePropertyValues == null) {
        //     Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
        //     Map<String, Object> values = new HashMap<> > (rpr);
        //     // 排除已经设置的属性
        //     values.remove("type");
        //     values.remove("driver-class-name");
        //     values.remove("url");
        //     values.remove("username");
        //     values.remove("password");
        //     dataSourcePropertyValues = new MutablePropertyValues(values);
        // }
        // dataBinder.bind(dataSourcePropertyValues);
    }

    /**
     * 初始化更多数据源
     *
     * @author SHANHY
     * @create 2016年1月24日
     */
    private void initCustomDataSources(Environment env) {
        // // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        // RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
        // String dsPrefixs = propertyResolver.getProperty("names");
        // for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
        //     Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
        //     DataSource ds = buildDataSource(dsMap);
        //     customDataSources.put(dsPrefix, ds);
        //     dataBinder(ds, env);
        // }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}