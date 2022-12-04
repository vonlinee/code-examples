package io.devpl.spring.data.jdbc;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * 配置一个数据源的所有配置项
 * @see org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
 */
public class DataSourceInformation implements BeanClassLoaderAware, InitializingBean {

    /**
     * 数据源配置信息
     */
    private DataSourceProperties dsProps;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dsProps != null) {
            System.out.println(dsProps);
        }
    }

    public String getName() {
        return dsProps.getName();
    }
}
