# sharding-sphere
sharding-sphere  分库分表 （springboot + mybatis + mysql + sharding-sphere ）


配置hosts
    ip 为执行了本地脚本的 地址
    
  10.121.20.13       local.mysql.com
  
>> 仅仅为了演示功能，仅定义3个表，user 、student、teacher
```
   user 表不分库，不分表
   student 、teacher 分表

```

需要注意的 库名中不能存在下划线 (_) ，不然启动会报错
