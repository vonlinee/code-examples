





官方文档：

https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/en-us/index.html#_spring_cloud_alibaba_nacos_config



同时加上两个依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```



DataId和GROUP



Nacos同springcloud-config一样，在项目初始化时，要保证先从配置中心进行配置拉取，拉取配置之后，才能保证项目的正常启动。

springboot中配置文件的加载是存在优先级顺序的，bootstrap优先级高于application，一般全局的配置放在bootsrap.yml，工程内部的配置放在application.yml中

```yaml
server:
  port: 3377

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: 101.33.212.245:8848
      config:
        server-addr: 101.33.212.245:8848
        file-extension: yaml  # 指定yaml格式的配置
        
$(spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
```

在application.yml中配置环境为开发环境

```yaml
spring:
  profiles:
    active: dev
```



通过Spring Cloud 原生注解@RefreshScope实现配置自动更新

```java
@RestController
@RefreshScope //支持Nacos的配置动态刷新
public class OrderNacosController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping(value = "/config/info")
    public String paymentInfo() {
        return configInfo;
    }
}
```



# Nacos配置规则

Nacos中的dataid的组成格式及与SpringBoot配置文件中的匹配规则

https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html

![image-20220227191557423](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227191557423-16459605643191.png)



配置文件的名字是

```
nacos-config-client-dev.yaml
```



# 新增配置



![image-20220227192239716](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227192239716.png)



![image-20220227192305705](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227192305705.png)





![image-20220227192702152](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227192702152.png)

点击发布后，启动客户端的主启动类，访问localhost:3377/config/info，会打印配置好的值nacos-config-center

在nacos控制台修改配置后，再次访问该接口，可以得到新的值



# Nacos配置中心核心概念



实际开发中，通常一个系统会准备dev开发环境

test测试环境prod生产环境。

如何保证指定环境启动时服务能正确读取到Nacos上相应环境的配置文件呢?



## 命名空间



![image-20220227193201755](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227193201755.png)



![image-20220227193212359](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227193212359.png)





Nacos默认的命名空间是public，Namespace主要用来实现隔离。

比方说我们现在有三个环境:-开发、测试、生产环境，我们就可以创建三个Namespace，不同的Namespace之间是隔离的。

为什么这么设计？

![image-20220227193317676](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227193317676.png)

通过spring.profile.active属性就能进行多环境下配置文件的读取

配置什么就加载什么，根据公式加载对应的配置文件





推荐配置方案

## Group分组方案



```yaml
server:
  port: 3377

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: 101.33.212.245:8848
      config:
        server-addr: 101.33.212.245:8848
        file-extension: yaml  # 指定yaml格式的配置
        group: TEST_GROUP  # 添加分组配置
```



## Namespace方案



![image-20220227194258144](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227194258144.png)

点击命名空间，然后点击新建命名空间

![image-20220227194317674](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227194317674.png)



```yaml
server:
  port: 3377

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: 101.33.212.245:8848
      config:
        server-addr: 101.33.212.245:8848
        file-extension: yaml  # 指定yaml格式的配置
        group: TEST_GROUP
        namespace: 419ae716-3223-42ea-b8d6-0a5f7d3f1023
```

此时加载的就是namespace为419ae716-3223-42ea-b8d6-0a5f7d3f1023下的，group为TEST_GROUP下的对应公式的配置文件，即

nacos-config-client-dev.yaml











# 集群配置



https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html

要3个和3个以上才能配置Nacos集群

Nginx集群配置，架构，Linux + Nginx集群 + Nacos集群 + MySQL



Nginx作为虚拟IP，为了数据安全

![image-20220227200134844](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227200134844.png)

新版的架构图有变化



## 持久化配置

默认Nacos使用嵌入式数据库实现数据的莴储。所以，如果启动多个默认配置下的Nacos节点，数据存储是存在一致性问题的，因为每个Nacos都内嵌了一个Derby数据库。为了解决这个问题，Nacos采用了集中式存储的方式来支持集群化部署，目前只支持MySQL的存储。



![image-20220227200231222](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227200231222.png)



![image-20220227200414416](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227200414416.png)



nacos-server-1.1.4\nacos\conf目录下找到sql脚本

![image-20220227200842957](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227200842957.png)

这个sql可以在下面的地址下载

https://github.com/alibaba/nacos/blob/master/distribution/conf/nacos-mysql.sql



Nacos部署环境：https://nacos.io/zh-cn/docs/deployment.html

修改修改 Nacos 配置文件，指向 MySQL 实例，替换其内嵌数据库

单机部署，配置持久化数据库MySQL

```properties
spring.datasource.platform=mysql  # 数据源平台

db.num=1
db.url.0=jdbc:mysql://11.162.196.16:3306/nacos_devtest?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=123456
```

重启Nacos，会从配置的数据库中加载配置，那么原先配置的Derby中的配置就没有了

## 集群配置



复制 cluster.conf 文件

```shell
[wl@VM-20-5-centos conf]# ll
total 88
-rw-r--r-- 1 wl root  1224 Jun 18  2021 1.4.0-ipv6_support-update.sql
-rw-r--r-- 1 wl root  9506 Jul 27  2021 application.properties
-rw-r--r-- 1 wl root  9506 Jul 27  2021 application.properties.example
-rw-r--r-- 1 wl root   670 Mar 18  2021 cluster.conf.example
-rw-r--r-- 1 wl root 31156 Jul 15  2021 nacos-logback.xml
-rw-r--r-- 1 wl root 10660 Jun 18  2021 nacos-mysql.sql
-rw-r--r-- 1 wl root  8795 Jun 18  2021 schema.sql
```



Nacos安装目录的目录结构如下：Nacos是基于springboot的

```shell
[wl@VM-20-5-centos nacos]# ll
total 44
drwxr-xr-x 4 wl root  4096 Dec 13 12:43 bin
drwxr-xr-x 2 wl root  4096 Jul 27  2021 conf
drwxr-xr-x 7 wl root  4096 Dec 29 23:50 data
-rw-r--r-- 1 wl root 16583 Mar 18  2021 LICENSE
drwxr-xr-x 2 wl root  4096 Feb 27 19:25 logs
-rw-r--r-- 1 wl root  1305 May 14  2020 NOTICE
drwxr-xr-x 2 wl root  4096 Dec 13 11:53 target
```

编辑，梳理出3台nacos集器的不同服务端口号

```shell
[root@localhost conf]# vim ./cluster.conf
#it is ip
#example
192.168.15.145
192.168.15.147
192.168.15.148
```

这个IP不能写127.0.0.1，必须是Linux命令hostname -i能够识别的IP，

如果是阿里云的直接用公网IP即可

三台 nacos 实例都需要做以上集群配置，至此关于 nacos 的配置结束了，可以尝试以集群模式启动三个 nacos 实例了

如果三台Nacos实例在同一台机器上（使用单机模拟集群），那么配置不同的端口号即可，例如：

```shell
192.168.15.144:8001
192.168.15.144:8002
192.168.15.144:8003
```

然后需要修改Nacos的启动脚本，因为Nacos的启动脚本并不支持选择端口启动参数

![image-20220227203531894](D:\Develop\Projects\Github\code-example\resources\notes\images\Nacos使用\image-20220227203531894.png)

或者直接将配置文件application.properties复制三份，每次启动时都修改一个端口号也可以







说明：如果三个实例以集群模式正常启动，那么分别访问三个实例的管理页就是展示以上登录页了。如果不能访问，则可能防火墙未开放 nacos 服务的端口，可执行如下命令



配置教程博客：

https://cloud.tencent.com/developer/article/1805561



