



Spring-Mybatis动态切换数据源

运行时报错，不管怎么百度都解决不了
Caused by: org.xml.sax.SAXException: Invalid system identifier: http://mybatis.org/dtd/mybatis-3-config.dtd

去掉spring-mybatis.xml中配置的下面内容即可：
<!--    引入jdbc配置文件-->
<bean id="propertyConfigurer"
class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
<property name="locations">
<list>
<!--要是有多个配置文件，只需在这里继续添加即可 -->
<value>classpath:properties/*.properties</value>
<value>classpath:*.xml</value>
</list>
</property>
</bean>







