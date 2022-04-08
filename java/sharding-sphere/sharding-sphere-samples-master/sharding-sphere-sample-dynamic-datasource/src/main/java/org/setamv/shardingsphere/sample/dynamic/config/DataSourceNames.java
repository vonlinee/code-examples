package org.setamv.shardingsphere.sample.dynamic.config;

/**
 * 数据源名称常量。
 * 总共包括五个数据源：<ul>
 *     <li>scm_ds：scm主库数据源</li>
 *     <li>scm_ds0：scm0分库数据源</li>
 *     <li>scm_ds1：scm1分库数据源</li>
 *     <li>sharding_ds：Sharding JDBC分片数据源，是包含scm_ds0和scm_ds1两个分库的分片数据源</li>
 *     <li>dynamic_ds：sharding_ds和scm_ds组成的动态路由数据源</li>
 * </ul>
 * @author setamv
 * @date 2021-04-16
 */
public final class DataSourceNames {

    private DataSourceNames() {

    }

    /**
     * scm主库数据源
     */
    public static final String SCM_DATA_SOURCE = "scm_ds";

    /**
     * scm1分库数据源
     */
    public static final String SCM0_DATA_SOURCE = "scm_ds0";

    /**
     * scm2分库数据源
     */
    public static final String SCM1_DATA_SOURCE = "scm_ds1";

    /**
     * Sharding JDBC分片数据源，是包含scm1DataSource和scm2DataSource两个分库的分片数据源
     */
    public static final String SHARDING_DATA_SOURCE = "sharding_ds";

    /**
     * shardingDataSource和scmDataSource组成的动态路由数据源
     */
    public static final String DYNAMIC_DATA_SOURCE = "dynamic_ds";
}
