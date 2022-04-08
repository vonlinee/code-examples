# sharding-sphere-sample-dynamic-datasource介绍
sharding-sphere-sample-dynamic-datasource模块演示了Sharding JDBC动态数据源切换的使用示例。

## 典型应用场景
sharding-sphere-sample-dynamic-datasource模块演示的样例适用的典型应用场景为：
系统运行多年后导致存在非常多的存量数据，需要做分库分表减轻数据库的压力。因为系统业务耦合太紧密，直接对系统做分库分表风险非常大，所以采用历史数据归档的方案：
系统将历史数据（即业务已完结的）迁移到归档库，并且归档的数据使用分库分表存储。非历史数据仍然存放在原来的数据库不变（以下对原来的数据库简称为主库）。
这种情况下，需要对主库和归档库分别配置数据源，并根据当前的查询条件判定是查询历史数据还是非历史数据，从而动态切换对应的数据源执行查询。

## 数据源配置
一共包含以下几个数据源（参见org.setamv.shardingsphere.sample.dynamic.config.DataSourceNames中的常量定义）：
+ scm_ds：scm主库数据源
+ scm_ds0：scm1分库数据源（用于存放部分年份的数据）
+ scm_ds1：scm2分库数据源（用于存放部分年份的数据）
+ sharding_ds：是包含scm_ds0和scm_ds1两个分库的Sharding JDBC分片数据源
+ dynamic_ds：sharding_ds和scm_ds组成的动态路由数据源。该动态路由数据源会根据当前查询的分片条件，将查询路由到scm_ds或sharding_ds
    路由的规则参考：org.setamv.shardingsphere.sample.dynamic.aspect.ServiceAspect.determineDynamicDataSource
    
## 分库分表的规则
参见 resource/sql/初始化脚本.sql 文件的说明。
    
## 使用说明
运行该样例程序，只需要执行以下步骤：
1. 安装MySQL数据库
2. 使用 resource/sql/初始化脚本.sql 初始化主库、分库分表的数据库和表
3. 启动 org.setamv.shardingsphere.sample.dynamic.DynamicDataSourceSampleApplication 

## 代码指引
+ 数据源动态路由接口演示：org.setamv.shardingsphere.sample.dynamic.controller.OperateLogController.query
+ 数据源动态路由注解：org.setamv.shardingsphere.sample.dynamic.spel.DynamicDataSourceRouter
    在Service方法上加上该注解，并在注解中指定分片条件的SpEL表达式，程序会自动从Service的入参中提取分片条件值，并根据条件值的判定结果做数据源的切换。