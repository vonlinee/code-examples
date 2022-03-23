如果是Eclipse：注意Web Project Settings 里Context Root设置
访问路径：http://localhost:8888/SpringMVC/index.jsp

Maven-webapp这个模板会自动添加Context Root为artifactId的值
<artifactId>SpringMVC</artifactId>
打包后的jar名称：SpringMVC-1.0.SNAPSHOT
可以修改artifactId的值然后验证Web Project Settings的值

# 静态资源映射

当在 DispatcherServlet 的< url-pattern >中配置拦截 “/” 时，除了*.jsp 不会拦截以外，其他所有的请求都会经过前端控制器进行匹配，此时静态资源，例如 .css、.js、*.jpg…… 就 会被前端控制器拦截，导致不能访问，出现404 问题。

1.通过 DefaultServlet 处理静态资源
<servlet-mapping>
	<servlet-name>default</servlet-name>
	<url-pattern>*.jpg</url-pattern>
</servlet-mapping>
<servlet-mapping>
	<servlet-name>default</servlet-name>
	<url-pattern>*.css</url-pattern>
</servlet-mapping>
<servlet-mapping>
	<servlet-name>default</servlet-name>
	<url-pattern>*.js</url-pattern>
</servlet-mapping>
<servlet-mapping>
	<servlet-name>default</servlet-name>
	<url-pattern>*.png</url-pattern>
</servlet-mapping>

2.通过 SpringMVC 的静态资源映射器处理静态资源
在 spring3.0.4 以后的 SpringMVC 模块提供了静态资源映射器组件。通过 mvc:resources标签配置静态资源映射器

<mvc:resources location="/img/" mapping="/img/**"></mvc:resources>
<mvc:resources location="/js/" mapping="/js/**"></mvc:resources>

3、通过<mvc:default-servlet-handler />处理静态资源

在 SpringMVC 的配置文件中配置<mvc:default-servlet-handler />后，会在 Spring MVC 上下文中定义一个org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler，它会像一个检查员，对进入 DispatcherServlet 的 URL 进行筛查，如果发现是静态资源的请求，就将该请求转由 Web 应用服务器默认的 Servlet 处理，如果不是静态资源的请求，才由DispatcherServlet 继续处理

<mvc:default-servlet-handler/>

HandlerMapping 请求路径映射















报错：Could not initialize class org.apache.maven.plugin.war.util.WebappStructureSerializer
https://blog.csdn.net/weixin_45611051/article/details/118712494
原因：POM中包含有maven-war-plugin插件，插件版本太低
在build中加入如下代码：
<build>
	<finalName>itrip-search</finalName>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-war-plugin</artifactId>
			<version>3.3.1</version>
		</plugin>
	</plugins>
</build>


JSP中报错：
The superclass "jakarta.servlet.http.HttpServlet" was not found on the Java Build Path





https://www.cnblogs.com/whx7762/p/7762665.html















