package coed.example.springboot.webcomponent;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;

public class IntializeServletAction implements ServletContextInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		Enumeration<String> names = servletContext.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = (String) servletContext.getAttribute(name);
			System.out.println(value);
		}
	}
}
