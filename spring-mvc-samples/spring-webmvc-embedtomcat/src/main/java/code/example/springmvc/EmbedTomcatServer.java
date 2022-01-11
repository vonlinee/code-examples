package code.example.springmvc;

import java.io.IOException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * 嵌入式Tomcat使用方法:https://blog.csdn.net/fenglllle/article/details/94406162
 * @author someone
 */
public class EmbedTomcatServer {
	
	//这里使用classpath作为webapp目录，实际可用根据情况设定，
	//这个目录下放置WEB-INF/web.xml文件，tomcat启动会加载这个文件。
	
	private static int port = 8080;
    private static String contextPath = "/";
 
    public static void start() throws LifecycleException, IOException {
        Tomcat tomcat = new Tomcat();
        String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(port);
        //tomcat.setConnector(new Connector());
        tomcat.addWebapp(contextPath, baseDir);
        tomcat.enableNaming();
        tomcat.start();
        tomcat.getServer().await();
    }
 
    public static void main(String[] args) throws IOException, LifecycleException {
        start();
    }
}
