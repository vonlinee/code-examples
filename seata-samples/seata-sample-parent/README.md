



TM: business-service
RM: order-service, storage-service

TC服务端







搭建RM



可以多个服务都用一个事务群组，也可以每个服务单独使用一个，但是要保证对应

```java
// service.vgroupMapping.${custom_name}-tx-group=default
service.vgroupMapping.order-service-tx-group=default
service.vgroupMapping.storage-service-tx-group=default
service.vgroupMapping.business-service-tx-group=default
```



启动，请求报错：seata之no available service ‘default‘ found

解决方案参考Githbu Issue：https://github.com/seata/seata/issues/2521



service.vgroupMapping.default_tx_group=default







搭建TM







































