package io.devpl.spring.boot.factories;

import io.devpl.sdk.beans.impl.map.BeanMap;
import io.devpl.spring.context.PropertyBindCallback;
import io.devpl.spring.data.jdbc.DataSourceManager;
import io.devpl.spring.data.jpa.entity.DataSourceInformation;
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
 * <p>
 * 即在配置加载阶段就完成数据库的初始化
 */
public class DataSourceInitializer implements SpringApplicationRunListener, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceInitializer.class);

    private final SpringApplication application;
    private final String[] args;

    private static final String PREFIX = "devpl.datasource";

    public DataSourceInitializer(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        String prefix = environment.getProperty("devpl.namespace.prefix.datasource");
        if (!StringUtils.hasText(prefix)) {
            prefix = PREFIX;
        }
        initializeMultiDataSource(prefix, bootstrapContext, environment);
    }

    /**
     * 初始化动态数据源
     * https://machbbs.com/v2ex/427466
     * https://www.jianshu.com/p/4feab6df384e
     * @param bootstrapContext 启动上下文
     * @param environment      运行环境
     */
    private void initializeMultiDataSource(String prefix, ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        MutablePropertySources propertySources = environment.getPropertySources();
        // 存放数据源配置信息
        List<DataSourceInformation> dataSourceInformations = new ArrayList<>();
        // 配置属性名称
        ConfigurationPropertyName cpn = ConfigurationPropertyName.of(prefix);
        Bindable<BeanMap> bindable = Bindable.of(BeanMap.class);
        for (PropertySource<?> source : propertySources) {
            if (!StringUtils.startsWithIgnoreCase(source.getName(), DevplConstant.NAME)) {
                continue;
            }
            // 如何把 Java properties 转换为具有层级结构的字典
            Binder binder = new Binder(ConfigurationPropertySources.from(source));
            BeanMap map = binder.bind(cpn, bindable, new PropertyBindCallback()).orElse(new BeanMap());
            // 获取所有的数据源名称
            String dataSourceNames = map.getString("name-list", "");
            if (!StringUtils.hasLength(dataSourceNames)) {
                continue;
            }
            Bindable<DataSourceInformation> dspBindable = Bindable.of(DataSourceInformation.class);
            // 数据库的ID
            for (String dsName : dataSourceNames.split("\\|")) {
                String dataSourceId = dsName.trim();
                if (!map.containsKey(dataSourceId)) {
                    LOG.warn("name-list contains {}, but cannot find the relative config item!", dataSourceId);
                    continue;
                }
                String psName = "devpl.datasource." + dataSourceId;
                Map<String, Object> dataSourceInfoMap = new HashMap<>();
                dataSourceInfoMap.put(psName + ".name", dataSourceId);
                Map<String, String> info = map.getMap(dataSourceId);
                if (info != null) {
                    info.forEach((k, v) -> {
                        dataSourceInfoMap.put(psName + "." + k, v);
                    });
                }
                OriginTrackedMapPropertySource dsPropertySource = new OriginTrackedMapPropertySource("DataSource-" + dataSourceId, dataSourceInfoMap);
                Binder dataSourceInfoBinder = new Binder(ConfigurationPropertySources.from(dsPropertySource));
                environment.getPropertySources().addLast(dsPropertySource);
                dataSourceInformations.add(dataSourceInfoBinder.bind(psName, dspBindable)
                        .orElse(new DataSourceInformation()));
            }
        }
        // 初始化数据源
        DataSourceManager manager = bootstrapContext.getOrElseSupply(DataSourceManager.class, DataSourceManager::new);
//        for (DataSourceInformation information : dataSourceInformations) {
//            LOG.info("register DataSource[{}]", information.getName());
//            // TODO 修改
//            manager.registerDataSource(information.getName(), null);
//        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
