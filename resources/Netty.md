ChannelInitializer：单例

```java
@Sharable
public abstract class ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter
```

@Sharable表示注解修饰的ChannelHandler可以在没有竞态条件（race condition）下被多次添加到一个或多个ChannelPipeline



在实际开发过程中，我们会遇到这样的问题，插入数据时想让数据库存在的时候就更新，不存在的时候就新增。
方案一：

先查询后插入，先到数据库查询一下，查到数据就更新，查询不到数据就新增，但是这种情况往往会发生很多错误，

比如在查询后判断是否存在时，其他线程已经插入完毕了，你这边还是判断不存在的，这时候就会重复插入或者报错。
方案二：

Mysql提供了ON DUPLICATE KEY UPDATE,保证了操作的原子性和数据的完整性

可以达到以下目的:

向数据库中插入一条记录：

若该数据的主键值/ UNIQUE KEY 已经在表中存在,则执行更新操作, 即UPDATE 后面的操作。

否则插入一条新的记录。

```sql
INSERT INTO table (a,b,c) VALUES
("1","2","2")
ON DUPLICATE KEY UPDATE
c = VALUES(c)
```

使用mybatis批量操作：

```sql
<insert id="insertHistory" parameterType="java.util.List">
    INSERT INTO sle_history (user_id,joke_id,content)
    VALUES
    <foreach collection="list" item="item" index="index" separator="," >
      (#{item.userId,jdbcType=BIGINT},#{item.jokeId,jdbcType=BIGINT},#{item.content,jdbcType=VARCHAR})
    </foreach>
    ON DUPLICATE KEY UPDATE
    content = VALUES(content)
</insert>
```

可以使用row_number()函数，mysql8.0开始 已经支持这个函数


