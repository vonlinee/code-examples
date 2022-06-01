package utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.startup.Bootstrap;

/**
	-Dcatalina.home=
	-Dcatalina.base=D:/Projects/apache-tomcat-9.0.60-src/home
	-Djava.endorsed.dirs=catalina-home/endorsed
	-Djava.io.tmpdir=catalina-home/temp
	-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
	-Djava.util.logging.config.file=D:/Projects/apache-tomcat-9.0.60-src/home/conf/logging.properties
 */
public class TomcatServer {
	
	static {
		System.setProperty("catalina.home", "D:/Projects/apache-tomcat-9.0.60-src/home");
		System.setProperty("catalina.base", "D:/Projects/apache-tomcat-9.0.60-src/home");
		System.setProperty("java.endorsed.dirs", "D:/Projects/apache-tomcat-9.0.60-src/home/endorsed");
		System.setProperty("java.io.tmpdir", "D:/Projects/apache-tomcat-9.0.60-src/home/temp");
		System.setProperty("java.util.logging.manager", "org.apache.juli.ClassLoaderLogManager");
		System.setProperty("java.util.logging.config.file", "D:/Projects/apache-tomcat-9.0.60-src/home/conf/logging.properties");
	}
	
	public static void main(String[] args) {
//    	String classpath = System.getProperty("java.class.path");
//    	Arrays.asList(classpath.split(";")).forEach(System.out::println);
		
		
		HashMap<String, Object> map = new HashMap<>();
		
		Map<String, Object> map1 = map;
		
		map1.containsKey("");
		
		map.putIfAbsent("self", map1);
		
		Bootstrap.main(args);
	}
}
