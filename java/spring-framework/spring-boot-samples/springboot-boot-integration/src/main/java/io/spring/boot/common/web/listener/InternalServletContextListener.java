package io.spring.boot.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用@WebListener注解，实现ServletContextListener接口
 */
@WebListener
public class InternalServletContextListener implements ServletContextListener {
	
	private static final Logger log = LoggerFactory.getLogger(InternalServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("ServletContex contextInitialized, serverInfo: {}", sce.getServletContext().getServerInfo());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("ServletContex contextDestroyed, serverInfo: {}", sce.getServletContext().getServerInfo());
	}
}
