package io.devpl.spring.boot.factories.listener;

import io.devpl.sdk.beans.MapBean;
import io.devpl.spring.context.PropertyBindCallback;
import io.devpl.spring.data.jdbc.DataSourceInformation;
import io.devpl.spring.data.jdbc.DataSourceManager;
import io.devpl.spring.utils.DevplConstant;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多数据源注册，在Environment准备完成后立刻进行注册
 */
public class MultiDataSourceRegistrar implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    /**
     * 加载多数据源配置，手动进行绑定
     * https://machbbs.com/v2ex/427466
     * https://www.jianshu.com/p/4feab6df384e
     */
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        // 存放数据源配置信息
        List<DataSourceInformation> dataSourceInformations = new ArrayList<>();
        // 配置属性名称
        ConfigurationPropertyName cpn = ConfigurationPropertyName.of("devpl.datasource");
        Bindable<MapBean> bindable = Bindable.of(MapBean.class);
        for (PropertySource<?> source : propertySources) {
            if (!StringUtils.startsWithIgnoreCase(source.getName(), DevplConstant.NAME)) {
                continue;
            }
            // 如何把 Java properties 转换为具有层级结构的字典
            Binder binder = new Binder(ConfigurationPropertySources.from(source));
            MapBean map = binder.bind(cpn, bindable, new PropertyBindCallback()).orElse(new MapBean());
            String dataSourceIds = map.getString("id", "");
            if (!StringUtils.hasLength(dataSourceIds)) {
                continue;
            }
            String[] split = dataSourceIds.split(",");
            Bindable<DataSourceInformation> dspBindable = Bindable.of(DataSourceInformation.class);
            // 数据库的ID
            for (String s : split) {
                String dataSourceId = s.trim();
                Map<String, Object> dataSourceInfoMap = new HashMap<>();
                dataSourceInfoMap.put("name", dataSourceId);
                dataSourceInfoMap.putAll(map.get(dataSourceId));
                String psName = "devpl.datasource." + dataSourceId;
                environment.getPropertySources().addLast(new OriginTrackedMapPropertySource(psName, dataSourceInfoMap));
                dataSourceInformations.add(binder.bind(psName, dspBindable).orElse(new DataSourceInformation()));
            }
        }

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DataSourceManager.class);
        beanDefinition.setSynthetic(true);
        Map<String, DataSourceInformation> metainfo = new HashMap<>();
        for (DataSourceInformation dataSourceProperties : dataSourceInformations) {
            metainfo.put(dataSourceProperties.getName(), dataSourceProperties);
        }
        ConstructorArgumentValues values = beanDefinition.getConstructorArgumentValues();
        values.addIndexedArgumentValue(0, metainfo);
        // registry.registerBeanDefinition("Devpl#DataSourceManager", beanDefinition);

    }
}
