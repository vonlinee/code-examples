package io.devpl.spring.boot.factories;

import io.devpl.sdk.beans.MapBean;
import io.devpl.spring.context.PropertyBindCallback;
import io.devpl.spring.data.jdbc.DataSourceInformation;
import io.devpl.spring.data.jdbc.DataSourceManager;
import io.devpl.spring.utils.DevplConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
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
 * 在Sping内部的Environment准备完毕后触发
 * 从Environment配置中加载所有的数据源信息，并且初始化数据源
 */
public class DataSourceInitializer implements SpringApplicationRunListener, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceInitializer.class);

    private final SpringApplication application;
    private final String[] args;

    public DataSourceInitializer(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        initializeMultiDataSource(bootstrapContext, environment);
    }

    /**
     * 初始化动态数据源
     * https://machbbs.com/v2ex/427466
     * https://www.jianshu.com/p/4feab6df384e
     * @param bootstrapContext
     * @param environment
     */
    private void initializeMultiDataSource(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
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
            // 获取所有的数据源名称
            String dataSourceIds = map.getString("name-list", "");
            if (!StringUtils.hasLength(dataSourceIds)) {
                continue;
            }
            Bindable<DataSourceInformation> dspBindable = Bindable.of(DataSourceInformation.class);
            // 数据库的ID
            for (String s : dataSourceIds.split("\\|")) {
                String dataSourceId = s.trim();
                Map<String, Object> dataSourceInfoMap = new HashMap<>();
                dataSourceInfoMap.put("name", dataSourceId);
                dataSourceInfoMap.putAll(map.get(dataSourceId));
                String psName = "devpl.datasource." + dataSourceId;
                environment.getPropertySources().addLast(new OriginTrackedMapPropertySource(psName, dataSourceInfoMap));
                dataSourceInformations.add(binder.bind(psName, dspBindable).orElse(new DataSourceInformation()));
            }
        }
        // 初始化数据源
        DataSourceManager manager = bootstrapContext.getOrElseSupply(DataSourceManager.class, DataSourceManager::new);
        for (DataSourceInformation information : dataSourceInformations) {
            LOG.info("register DataSource[{}]", information.getName());
            manager.registerDataSource(information.getName(), information);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
