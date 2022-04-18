package io.spring.boot.common;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

public class ApplicationServletInitializer extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(ApplicationServletInitializer.class);
	
    @Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	@Override
	protected void deregisterJdbcDrivers(ServletContext servletContext) {
		super.deregisterJdbcDrivers(servletContext);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		return super.createRootApplicationContext(servletContext);
	}

	@Override
	protected SpringApplicationBuilder createSpringApplicationBuilder() {
		return super.createSpringApplicationBuilder();
	}

	@Override
	protected WebApplicationContext run(SpringApplication application) {
		return super.run(application);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources();
    }
}
