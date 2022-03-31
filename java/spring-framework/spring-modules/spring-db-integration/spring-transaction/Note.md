



[关于：Table '项目名称..hibernate_sequence' doesn't exist的解决方法_Interesting_Talker的博客-CSDN博客](https://blog.csdn.net/Interesting_Talent/article/details/81454104)













解决方案：
这个是主键自增长策略问题。

将ID生成略组改成@GeneratedValue(strategy = GenerationType.IDENTITY).

 

 关于主键策略
@GeneratedValue:主键的产生策略，通过strategy属性指定。 
主键产生策略通过GenerationType来指定。GenerationType是一个枚举，它定义了主键产生策略的类型。 
1、AUTO自动选择一个最适合底层数据库的主键生成策略。如MySQL会自动对应auto increment。这个是默认选项，即如果只写@GeneratedValue，等价于@GeneratedValue (strategy=GenerationType.AUTO)。

2、IDENTITY　表自增长字段，Oracle不支持这种方式。

3、SEQUENCE　通过序列产生主键，MySQL不支持这种方式。

4、TABLE　通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。不同的JPA实现商生成的表名是不同的，如 OpenJPA生成openjpa_sequence_table表，Hibernate生成一个hibernate_sequences表，而TopLink则生成sequence表。这些表都具有一个序列名和对应值两个字段，如SEQ_NAME和SEQ_COUNT。





hibernate type=MyISAM的错误

https://blog.csdn.net/jerny2017/article/details/81982454



hibernate.cfg.xml配置了下面语句后：

<property name="hibernate.hbm2ddl.auto">update</property>

会通过hbm生成一个sql语句来创建表，

如果配置了下面的语句：

<property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

而且你使用的mysql是5.x,那么会报错： type=MyISAM

解决方法是：

配置改成：

```xml
<property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>
```

MySQL5Dialect： An SQL dialect for MySQL 5.x specific features.



![image-20220228145927044](\images\异常调用栈.png)







# 隔离级别













# 事务传播





































