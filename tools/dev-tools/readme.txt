
使用手册
https://github.com/moshowgame/SpringBootCodeGenerator/blob/master/README.md


其他类似的产品

GitHub 14.7k+ star  功能非常全面，低代码平台，有面向企业的商业产品
https://github.com/jeecgboot/jeecg-boot


原项目用的Springboot2.6.6版本，换成2.3.12.RELEASE
因为增加了Knife4j，原来的2.6.6版本启动报错，因此换成2.3.12.RELEASE




默认情况下Spring Boot 使用WebMvcAutoConfiguration中配置的各种属性。

默认映射的文件夹有：
classpath:/META-INF/resources
classpath:/resources
classpath:/static
classpath:/public
优先级顺序为：META-INF/resources > resources > static > public


spring boot访问静态资源：
1. static目录用来存放js、css、图片等静态资源. . .
2. templates目录用来存放html页面. . .

spring boot默认将/**静态资源访问映射到以下目录：


显而易见，templates目录下的页面文件是不能直接访问的，需要通过Controller进行访问（好像是websecurity权限控制的原因）。
与我们在做ssm项目时候的WEB-INF目录一样，需要通过服务器内部进行访问，即走控制器–服务–视图解析器这个流程。

要访问templates目录下的html页面，还需要引入下面这个模板引擎，然后才能通过Controller来进行访问
<!--访问静态资源-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

或者Freemarker也可以，templates目录下是由模板引擎访问的


指定了spring.profiles.active为dev时
application.properties不会覆盖application-dev.yml的配置，application-dev.properties会




maven检查依赖
通过在项目跟路径下执行mvn dependency:tree查看项目的完整依赖树

查看依赖树中包含某个groupId和artifactId的依赖链（注意-Dincludes后面是等于号）
mvn dependency:tree -Dincludes=com.alibaba:fastjson:

查看依赖树中包含某个artifactId的依赖链（artifactId前面加上冒号）
mvn dependency:tree -Dincludes=:fastjson:

查看依赖树中包含某个groupId的依赖链（-Dincludes后面跟上groupId）
mvn dependency:tree -Dincludes=com.alibaba:

mvn dependency:tree -Dincludes=javax.servlet


2、常用maven命令
clean：清除目标目录中的生成结果（对项目进行清理 –删除target目录，也就是将class文件等删除）。

install：本地项目发布至本地仓库(在本地库中安装jar –将压缩文件(jar或者war)上传到本地仓库)

package:测试并且打包( –生成压缩文件：java项目#jar包；web项目#war包，也是放在target目录下)

compile：编译源代码( –src/main/java目录java源码编译生成class （target目录下）)

test：运行项目中的单元测试( –src/test/java 目录编译)

mvn deploy：部署|发布　　–将压缩文件上传私服

tomcat*:run：启动tomcat，前提是在项目的pom.xml文件中添加了tomcat插件

dependency:sources：下载项目依赖的jar包的源码包

generate-sources:构建源码,也就是打包生成jar

source:jar:生成带源码的jar用于导入使用

