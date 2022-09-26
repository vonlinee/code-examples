package io.devpl.spring.data.jdbc;

import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * 数据源注册中心，保存数据库的元数据信息
 *
 * @see RoutingDynamicDataSource
 */
public class DataSourceManager implements InitializingBean {

    /**
     * 存储数据源的元数据信息，根据这些信息来创建数据源实例
     */
    private Map<String, DataSourceInformation> information;

    public Map<String, DataSourceInformation> getDataSourceInformation() {
        return this.information;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.information != null) {
            System.out.println(information);
        }
    }
}
