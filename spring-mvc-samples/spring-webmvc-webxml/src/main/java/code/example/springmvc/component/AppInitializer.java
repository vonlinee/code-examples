package code.example.springmvc.component;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppInitializer implements ServletContainerInitializer {

	/**
	 * @param c 	要用到的类型
	 * @param ctx 	上下文容器
	 */
	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		System.out.println("ServletContainerInitializer  ========================");
	}
}
