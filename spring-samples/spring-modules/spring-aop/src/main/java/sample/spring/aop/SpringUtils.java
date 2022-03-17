package sample.spring.aop;

import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringUtils {

	public static ApplicationContext loadContext(Class<?> clazz, String xml) {
		URL resource = clazz.getResource(xml);
		if (resource == null) {
			throw new NullPointerException(String.format("无法加载XML文件%s", xml));
		}
		return new FileSystemXmlApplicationContext(resource.toExternalForm());
	}
}
