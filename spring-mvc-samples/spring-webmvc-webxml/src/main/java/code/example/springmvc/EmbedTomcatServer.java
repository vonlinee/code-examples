package code.example.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

/**
 * 嵌入式Tomcat使用方法:https://blog.csdn.net/fenglllle/article/details/94406162
 * @author someone
 */
public class EmbedTomcatServer {
	
	private static final int port = 8888;
	
	//这里使用classpath作为webapp目录，实际可用根据情况设定，
	//这个目录下放置WEB-INF/web.xml文件，tomcat启动会加载这个文件。
    private static final String contextPath = "/";
 
    //嵌入式的Tomcat如果我想修改类似context.xml中Resources的属性
    //嵌入式tomcat也会读取/META-INF/context.xml;直接在这个文件配置就是了
    
    public static void start() throws LifecycleException, IOException {
        Tomcat tomcat = new Tomcat();
        String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        tomcat.setBaseDir(baseDir); // ./target/classes
        tomcat.setPort(port);
        Context context = tomcat.addWebapp(contextPath, baseDir);
        //System.out.println(context);//StandardEngine[Tomcat].StandardHost[localhost].StandardContext[/mvc]
        tomcat.enableNaming();
        //读取web.xml
        tomcat.start();  //启动Tomcat服务器
        Server server = tomcat.getServer(); //StandardServer
        if (server instanceof StandardServer) {
        	StandardServer ss = (StandardServer) server;
        	ss.await(); //堵塞
		}
    }

    //LifecycleBase.start()
    
    //https://blog.csdn.net/fenglllle/article/details/96590484
    //嵌入式tomcat的不使用web.xml原理分析
    //明白Spring Boot是如何在没有web.xml的的情况下实现web能力
    
    //Oracle Java EE : https://docs.oracle.com/javaee/7/api/javax/servlet/ServletContainerInitializer.html
    
    private static void initConnector(Tomcat tomcat) {
    	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    	tomcat.setConnector(connector);
    }
    
    public static void main(String[] args) throws IOException, LifecycleException {
        start();
    }
    
    public void log() {
    	//默认情况下，嵌入式Tomcat使用JDK提供的日志记录配置.如果您尚未更改配置
    	//则仅配置ConsoleHandler.如果要以编程方式添加FileHandler，则可以将其添加到root记录器中.
    	//这是一个通过在INFO级别附加消息来写入文件catalina.out的示例.这适用于Tomcat 6.x和7.x
    	Logger logger = Logger.getLogger("");
    	Handler fileHandler = null;
		try {
			fileHandler = new FileHandler("catalina.out", true);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
    	fileHandler.setFormatter(new SimpleFormatter());
    	fileHandler.setLevel(Level.INFO);
    	try {
			fileHandler.setEncoding("UTF-8");
		} catch (SecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	logger.addHandler(fileHandler);
    }
    
}
