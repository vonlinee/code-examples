package io.devpl.spring.data.jdbc;

import io.devpl.sdk.beans.MapBean;
import io.devpl.spring.Devpl;
import io.devpl.spring.context.PropertyBindCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源注册<>br/>
 * 启动动态数据源请在启动类中（如SpringBootSampleApplication）
 * 添加 @Import(DataSourceRegistrar.class)
 *
 * @author http://blog.csdn.net/catoop/
 */
public class DataSourceRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceRegistrar.class);

    /**
     * 数据源信息
     */
    private List<DataSourceInformation> dspList;

    // 如配置文件中未指定数据源类型，使用该默认值
    private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";

    private Map<String, DataSource> customDataSources = new HashMap<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DataSourceManager.class);
        beanDefinition.setSynthetic(true);
        Map<String, DataSourceInformation> metainfo = new HashMap<>();
        for (DataSourceInformation dataSourceProperties : dspList) {
            metainfo.put(dataSourceProperties.getName(), dataSourceProperties);
        }
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("information", metainfo);
        registry.registerBeanDefinition("devplDataSourceRegistry", beanDefinition);
    }

    /**
     * 加载多数据源配置，手动进行绑定
     * https://machbbs.com/v2ex/427466
     * https://www.jianshu.com/p/4feab6df384e
     */
    @Override
    public void setEnvironment(Environment env) {
        if (env instanceof AbstractEnvironment) {
            AbstractEnvironment environment = ((AbstractEnvironment) env);
            MutablePropertySources sources = environment.getPropertySources();
            for (PropertySource<?> source : sources) {
                if (!StringUtils.startsWithIgnoreCase(source.getName(), Devpl.NAME)) {
                    continue;
                }
                // 如何把 Java properties 转换为具有层级结构的字典
                Iterable<ConfigurationPropertySource> from = ConfigurationPropertySources.from(source);
                Binder binder = new Binder(from);
                ConfigurationPropertyName cpn = ConfigurationPropertyName.of("devpl.datasource");
                Bindable<MapBean> bindable = Bindable.of(MapBean.class);
                MapBean map = binder.bind(cpn, bindable, new PropertyBindCallback()).orElse(new MapBean());
                String dataSourceIds = map.getString("id", "");
                if (!StringUtils.hasLength(dataSourceIds)) {
                    continue;
                }
                String[] split = dataSourceIds.split(",");
                if (dspList == null) dspList = new ArrayList<>(split.length);
                Bindable<DataSourceInformation> dspBindable = Bindable.of(DataSourceInformation.class);
                // 数据库的ID
                for (String s : split) {
                    String dataSourceId = s.trim();
                    Map<String, Object> dataSourceInfoMap = new HashMap<>();
                    dataSourceInfoMap.put("name", dataSourceId);
                    dataSourceInfoMap.putAll(map.get(dataSourceId));
                    String psName = "devpl.datasource." + dataSourceId;
                    environment.getPropertySources().addLast(new OriginTrackedMapPropertySource(psName, dataSourceInfoMap));
                    dspList.add(binder.bind(psName, dspBindable).orElse(new DataSourceInformation()));
                }
                // 创建数据源实例
                for (DataSourceInformation dsp : dspList) {
                    customDataSources.put(dsp.getName(), dsp.initializeDataSourceBuilder().build());
                }
            }
        }
    }
}