

缓存可以将数据保存在内存中，是互联网系统常常用到的。目前流行的缓存服务器有 MongoDB、Redis、Ehcache 等。
缓存是在计算机内存上保存的数据，读取时无需再从磁盘读入，因此具备快速读取和使用的特点。

和大多数持久化框架一样，MyBatis 提供了一级缓存和二级缓存的支持。默认情况下，MyBatis 只开启一级缓存

## 一级缓存

一级缓存是基于 PerpetualCache（MyBatis自带）的 HashMap 本地缓存，作用范围为 session 域内。当 session flush（刷新）或者 close（关闭）之后，该 session 中所有的 cache（缓存）就会被清空。

在参数和 SQL 完全一样的情况下，我们使用同一个 SqlSession 对象调用同一个 mapper 的方法，往往只执行一次 SQL。因为使用 SqlSession 第一次查询后，MyBatis 会将其放在缓存中，再次查询时，如果没有刷新，并且缓存没有超时的情况下，SqlSession 会取出当前缓存的数据，而不会再次发送 SQL 到数据库。

由于 SqlSession 是相互隔离的，所以如果你使用不同的 SqlSession 对象，即使调用相同的 Mapper、参数和方法，MyBatis 还是会再次发送 SQL 到数据库执行，返回结果。





## 二级缓存



二级缓存是全局缓存，作用域超出 session 范围之外，可以被所有 SqlSession 共享。

一级缓存缓存的是 SQL 语句，二级缓存缓存的是结果对象。



MyBatis 的全局缓存配置需要在 mybatis-config.xml 的 settings 元素中设置，代码如下

```xml
<settings>
    <setting name="cacheEnabled" value="true" />
</settings>
```

在 mapper 文件（如 WebMapper.xml）中设置缓存，默认不开启缓存。需要注意的是，二级缓存的作用域是针对 mapper 的 namescape 而言，即只有再次在同一个 namescape 内查询才能共享这个缓存，代码如下。

```xml
<mapper namescape="net.biancheng.WebsiteMapper">
    <!-- cache配置 -->
    <cache
        eviction="FIFO"
        flushInterval="60000"
        size="512"
        readOnly="true" />
</mapper>
```

以上属性含义如下：

| 属性          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| eviction      | 代表的是缓存回收策略，目前 MyBatis 提供以下策略。LRU：使用较少，移除最长时间不用的对象；FIFO：先进先出，按对象进入缓存的顺序来移除它们；SOFT：软引用，移除基于垃圾回收器状态和软引用规则的对象；WEAK：弱引用，更积极地移除基于垃圾收集器状态和弱引用规则的对象。 |
| flushInterval | 刷新间隔时间，单位为毫秒，这里配置的是 100 秒刷新，如果省略该配置，那么只有当 SQL 被执行的时候才会刷新缓存。 |
| size          | 引用数目，正整数，代表缓存最多可以存储多少个对象，不宜设置过大。设置过大会导致内存溢出。这里配置的是 1024 个对象。 |
| readOnly      | 只读，默认值为 false，意味着缓存数据只能读取而不能修改，这样设置的好处是可以快速读取缓存，缺点是没有办法修改缓存。 |



对于 MyBatis 缓存仅作了解即可，因为面对一定规模的数据量，内置的 Cache 方式就派不上用场了，并且对查询结果集做缓存并不是 MyBatis 所擅长的，它专心做的应该是 SQL 映射。对于缓存，采用 OSCache、Memcached 等专门的缓存服务器来做更为合理





