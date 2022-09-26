package io.devpl.spring.boot.factories;

import io.devpl.sdk.beans.MapBean;
import io.devpl.spring.boot.DevplSpringApplication;
import io.devpl.spring.context.PropertyBindCallback;
import io.devpl.spring.context.SpringContext;
import io.devpl.spring.data.jdbc.DataSourceInformation;
import io.devpl.spring.data.jdbc.DynamicDataSource;
import io.devpl.spring.utils.DevplConstant;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在ApplicationContext创建之前注册一些Bean实例
 */
public class DevplBootstrap implements SpringApplicationRunListener, Ordered {

    private final SpringApplication application;
    private final String[] args;

    public DevplBootstrap(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        if (application instanceof DevplSpringApplication) {
            ((DevplSpringApplication) application).setBootstrapContext(bootstrapContext);
        }
        bootstrapContext.registerIfAbsent(DynamicDataSource.class, context -> new DynamicDataSource());
        bootstrapContext.registerIfAbsent(SpringContext.class, context -> SpringContext.INSTANCE);
        SpringContext.INSTANCE.setBootstrapContext(bootstrapContext);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        SpringContext.INSTANCE.setApplicationContext(context);
        // 上下文未刷新之前，不能通过getBean获取Bean对象
        SpringContext.INSTANCE.setBeanFactory(context.getBeanFactory());
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        SpringContext.INSTANCE.setEnvironment(environment);
        initializeDynamicDataSource(bootstrapContext, environment);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 初始化动态数据源
     * https://machbbs.com/v2ex/427466
     * https://www.jianshu.com/p/4feab6df384e
     * @param bootstrapContext
     * @param environment
     */
    private void initializeDynamicDataSource(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        MutablePropertySources propertySources = environment.getPropertySources();
        // 存放数据源配置信息
        List<DataSourceInformation> dataSourceInformations = new ArrayList<>();
        // 配置属性名称
        ConfigurationPropertyName cpn = ConfigurationPropertyName.of("devpl-jdbc.datasource");
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
        DynamicDataSource dynamicDataSource = bootstrapContext.get(DynamicDataSource.class);
        for (DataSourceInformation information : dataSourceInformations) {
            dynamicDataSource.addDataSource(information.getName(), information);
        }
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        DynamicDataSource dataSource = context.getBean(DynamicDataSource.class);

        System.out.println(dataSource);
    }
}
