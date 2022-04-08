





笔记：https://blog.csdn.net/unique_perfect/article/details/116134490

开发者文档：https://shardingsphere.apache.org/document/current/cn/dev-manual/sharding/

# 基本概念

逻辑表：水平拆分的数据表的总称。例：订单数据表根据主键尾数拆分为10张表，分别是 torder0 、 torder1 到torder9 ，他们的逻辑表名为 t_order 

真实表：在分片的数据库中真实存在的物理表。即上个示例中的 torder0 到 torder9 。

数据节点：数据分片的最小物理单元。由数据源名称和数据表组成，例：ds0.torder_0 。

绑定表：指分片规则一致的主表和子表。例如：torder 表和 torderitem 表，均按照 orderid 分片,绑定表之间的分区键完全相同，则此两张表互为绑定表关系。绑定表之间的多表关联查询不会出现笛卡尔积关联，关联查询效率将大大提升。

# 水平拆分

按水平拆分创建数据库与数据库表，目的也是为解决单表数据量大

```sql
-- 创建订单库order_db
DROP DATABASE IF EXISTS `order_db`;
CREATE DATABASE `order_db` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
USE order_db;
-- 在order_db中创建t_order_1、t_order_2表
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
`order_id` bigint(20) NOT NULL COMMENT '订单id',
`price` decimal(10, 2) NOT NULL COMMENT '订单价格',
`user_id` bigint(20) NOT NULL COMMENT '下单用户id',
`status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态',
PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2` (
`order_id` bigint(20) NOT NULL COMMENT '订单id',
`price` decimal(10, 2) NOT NULL COMMENT '订单价格',
`user_id` bigint(20) NOT NULL COMMENT '下单用户id',
`status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态',
PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

```

Maven依赖

```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding‐jdbc‐spring‐boot‐starter</artifactId>
    <version>4.0.0‐RC1</version>
</dependency>
```

在application.properties中配置规则，分片规则配置是sharding-jdbc进行对分库分表操作的重要依据，
配置内容包括：数据源、主键生成策略、分片策略等。

```properties
server.port=56081
spring.application.name = sharding‐jdbc‐simple‐demo
server.servlet.context‐path = /sharding‐jdbc‐simple‐demo
spring.http.encoding.enabled = true
spring.http.encoding.charset = UTF‐8
spring.http.encoding.force = true
spring.main.allow‐bean‐definition‐overriding = true
mybatis.configuration.map‐underscore‐to‐camel‐case = true
# 以下是分片规则配置
# 定义数据源的名称，多个用逗号分开
spring.shardingsphere.datasource.names = m1
# 配置m1数据源属性
spring.shardingsphere.datasource.m1.type = com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver‐class‐name = com.mysql.jdbc.Driver
spring.shardingsphere.datasource.m1.url = jdbc:mysql://localhost:3306/order_db?createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m1.username = root
spring.shardingsphere.datasource.m1.password = 123456
# 指定t_order表的数据分布情况，配置数据节点
spring.shardingsphere.sharding.tables.t_order.actual‐data‐nodes = m1.t_order_$‐>{1..2}
# 指定t_order表的主键列
spring.shardingsphere.sharding.tables.t_order.key‐generator.column=order_id
# 指定t_order表的主键生成策略为SNOWFLAKE
spring.shardingsphere.sharding.tables.t_order.key‐generator.type=SNOWFLAKE
# 指定t_order表的分片策略，分片策略包括分片键(根据哪个列应用分片策略)和分片算法
spring.shardingsphere.sharding.tables.t_order.table‐strategy.inline.sharding‐column = order_id
spring.shardingsphere.sharding.tables.t_order.table‐strategy.inline.algorithm‐expression =
t_order_$‐>{order_id % 2 + 1}
# 打开sql输出日志
spring.shardingsphere.props.sql.show = true
swagger.enable = true
logging.level.root = info
logging.level.org.springframework.web = info
logging.level.com.itheima.dbsharding = debug
logging.level.druid.sql = debug
```

上面的配置含义如下：

1. 首先定义数据源m1，并对m1进行实际的参数配置。
2. 指定t_order表的数据分布情况，他分布在m1.t_order_1，m1.t_order_2这两张表中
3. 指定t_order表的主键生成策略为SNOWFLAKE，SNOWFLAKE是一种分布式自增算法，保证id全局唯一
4. 定义t_order分片策略，order_id为偶数的数据落在t_order_1，为奇数的落在t_order_2，分表策略的表达式为t_order_$->{order_id % 2 + 1}



行表达式

```plain
```



使用MyBatis操作数据库，一个insert和一个select

```java
@Mapper
@Component
public interface OrderMapper {

    /**
     * 新增订单
     * @param price  订单价格
     * @param userId 用户id
     * @param status 订单状态
     * @return
     */
    @Insert("insert into t_order(price,user_id,status) value(#{price},#{userId},#{status})")
    int insertOrder(@Param("price") BigDecimal price, @Param("userId") Long userId, @Param("status") String status);

    /**
     * 根据id列表查询多个订单
     * @param orderIds 订单id列表
     * @return
     */
    @Select({"<script>" +
            "select " +
            " * " +
            " from t_order t" +
            " where t.order_id in " +
            "<foreach collection='orderIds' item='id' open='(' separator=',' close=')'>" +
            " #{id} " +
            "</foreach>" +
            "</script>"})
    List<Map<String, Object>> selectOrderbyIds(@Param("orderIds") List<Long> orderIds);
}
```



yaml配置方式：Spring Boot Yaml 配置

```yaml
server:
  port: 56081
  servlet:
    context-path: /sharding-jdbc-simple-demo
spring:
  application:
    name: sharding-jdbc-simple-demo
  http:
    encoding:
      enabled: true
      charset: utf-8
      force: true
  main:
    allow-bean-definition-overriding: true
  shardingsphere:
    datasource:
      names: m1
      m1:
        type: com.alibaba.druid.pool.DruidDataSource
        driverClassName: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/order_db?useUnicode=true
        username: root
        password: mysql
    sharding:
      tables:
        t_order:
          actualDataNodes: m1.t_order_$->{1..2}
          tableStrategy:
            inline:
              shardingColumn: order_id
              algorithmExpression: t_order_$->{order_id % 2 + 1}
          keyGenerator:
            type: SNOWFLAKE
            column: order_id
    props:
      sql:
        show: true
mybatis:
  configuration:
    map-underscore-to-camel-case: true
swagger:
  enable: true
logging:
  level:
    root: info
    org.springframework.web: info
    com.itheima.dbsharding: debug
    druid.sql: debug
```

Java 配置 添加配置类：

```java
@Configuration
public class ShardingJdbcConfig {
    // 配置分片规则 定义数据源
    public Map<String, DataSource> createDataSourceMap() {
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/order_db?useUnicode=true");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        Map<String, DataSource> result = new HashMap<>();
        result.put("m1", dataSource1);
        return result;
    }
    // 定义主键生成策略
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        return new KeyGeneratorConfiguration("SNOWFLAKE", "order_id");
    }

    // 定义t_order表的分片策略
    public TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order", "m1.t_order_$->{1..2}");
        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_$->{order_id % 2 + 1}"));
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }

    // 定义sharding-Jdbc数据源
    @Bean
    public DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        //spring.shardingsphere.props.sql.show = true
        Properties properties = new Properties();
        properties.put("sql.show", "true");
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
    }
}
```







```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJdbcSimpleDemoBootstrap.class})
public class OrderDaoTest {
    @Autowired
    private OrderMapper orderMapper;
    @Test
    public void testInsertOrder(){
        for (int i = 0 ; i<10; i++){
            orderMapper.insertOrder(new BigDecimal((i+1)*5),1L,"WAIT_PAY");
        }
    }
    
    @Test
    public void testSelectOrderbyIds(){
        List<Long> ids = new ArrayList<>();
        ids.add(373771636085620736L);
        ids.add(373771635804602369L);
        List<Map> maps = orderMapper.selectOrderbyIds(ids);
        System.out.println(maps);
    }
}
```



# 执行原理



























