package code.example.springmvc;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.util.LifecycleBase;

/**
 * 嵌入式tomcat的不使用web.xml原理分析,明白Spring Boot是如何在没有web.xml的的情况下实现web能力
 * 参考博客：https://blog.csdn.net/fenglllle/article/details/96590484 
 * Oracle Java EE:https://docs.oracle.com/javaee/7/api/javax/servlet/ServletContainerInitializer.html
 * @author someone
 */
public class EmbedTomcatServer {

	private static int port = 8080;
	private static String contextPath = "/";

	/**
	 * 访问：http://localhost:8080/hello，会请求到HelloServlet，且HelloServlet是Lazy Intialization
	 */
	
	public static void start() throws LifecycleException, IOException, ServletException {
		Tomcat tomcat = new Tomcat();
		String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		tomcat.setBaseDir(baseDir);
		tomcat.setPort(port);
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setPort(port);
		tomcat.setConnector(connector);
		tomcat.addWebapp(contextPath, baseDir);
		tomcat.enableNaming();
		// tomcat.getConnector();  手动创建
		tomcat.start();
		tomcat.getServer().await();
	}

	public static void main(String[] args) throws IOException, LifecycleException, ServletException {
		start();
	}
}
//嵌入式tomcat不使用web.xml是由于servlet 3.0的ServletContainerInitializer的引入。Spring Boot也是这样使用的