package code.example.springmvc;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

/**
 * https://www.yuque.com/xinxinyinyu/hb0903/hc9lx9
 * created on 2022年7月28日
 */
public class TomcatMain {

	public static void main(String[] args) throws LifecycleException {
		// 自己写Tomcat的启动源码
		Tomcat tomcat = new Tomcat();

		// 指定端口
		tomcat.setPort(8888);
		tomcat.setHostname("localhost");
		// 指定tomcat根路径
		tomcat.setBaseDir(".");

		// 指定tomcat项目路径
		Context context = tomcat.addWebapp("/boot", System.getProperty("user.dir") + "/src/main");
		// 给Tomcat里面添加一个Servlet
		Wrapper hello = tomcat.addServlet("/boot", "hello", new HelloServlet());

		hello.addMapping("/66"); // 指定处理的请求

		tomcat.start();// 启动tomcat 注解版MVC利用Tomcat SPI机制

		tomcat.getServer().await(); // 服务器等待

	}

}
