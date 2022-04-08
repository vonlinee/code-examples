# sharding-sphere-sample-dynamic-config介绍
sharding-sphere-sample-dynamic-config模块演示了运行时动态设置Sharding JDBC分片规则。

## 典型应用场景
sharding-sphere-sample-dynamic-config模块演示的样例适用的典型应用场景为：
系统需要分片的表是在服务运行时动态创建的，即服务启动之后会动态新增需要分库分表的表，如果需要对该表做分库分表，又不能重启应用的服务，则需要对Sharding JDBC的分片规则进行运行时动态设置。

## 运行时动态设置Sharding JDBC分片规则的原理
运行时动态设置Sharding JDBC分片规则的核心是重写 org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource 类，
因为该类里面的分片规则属性都是final类型的，不能修改，所以必须重写该类，参考实现：org.setamv.shardingsphere.starter.config.CustomizedShardingDataSource，
该类的refreshShardingRule方法可以重新设置分片规则。
然后在 org.setamv.shardingsphere.starter.config.DataSourceConfig.dataSource 中配置数据源的时候使用自定义的分片数据源类。

## 数据源配置

    
## 分库分表的规则
参考 resources/sql/初始化脚本.sql 中的说明
    
## 使用说明


## 代码指引

## 请求
### 新增订单
```
curl -X POST -s -H "Content-Type:application/json" -d '{  
    "orderDate": "2021-05-09",		
    "amount": 16,		
    "creatorId": 101,	
    "orderDetails": [{	
    "productId": 1,	
    "quantity": 10,	
    "price": 1.1,	
    "creatorId": 1	
    }, {				
        "productId": 2,	
        "quantity": 5,	
        "price": 1,		
        "creatorId": 1	
    }]					
}' "http://localhost:8080/order/add"
```

### 获取订单详情
```
curl -X GET -s "http://localhost:8080/order/get/598097168363945984"
```