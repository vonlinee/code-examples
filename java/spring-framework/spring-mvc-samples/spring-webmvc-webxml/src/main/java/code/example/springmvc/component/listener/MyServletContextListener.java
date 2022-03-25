package code.example.springmvc.component.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//博客：https://blog.csdn.net/qq_15204179/article/details/82055448
@WebListener
public class MyServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Object source = sce.getSource();
		ServletContext context = sce.getServletContext();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
