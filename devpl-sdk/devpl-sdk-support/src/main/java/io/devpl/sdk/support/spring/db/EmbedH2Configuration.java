package io.devpl.sdk.support.spring.db;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * https://blog.csdn.net/weixin_30266829/article/details/99953687
 * org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration
 */
@Slf4j
@EnableConfigurationProperties(H2ConsoleProperties.class)
@Configuration(proxyBeanMethods = false)
public class EmbedH2Configuration {

    @Bean
    @ConditionalOnProperty(prefix = "devpl.h2.console", name = "enabled", havingValue = "true", matchIfMissing = false)
    @ConditionalOnClass(WebServlet.class)
    @ConditionalOnMissingBean(H2ConsoleAutoConfiguration.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public H2ConsoleProperties properties() {
        H2ConsoleProperties h2ConsoleProperties = new H2ConsoleProperties();
        h2ConsoleProperties.setEnabled(true);
        h2ConsoleProperties.setPath("/console");
        h2ConsoleProperties.getSettings().setTrace(true);
        h2ConsoleProperties.getSettings().setWebAllowOthers(false);
        return h2ConsoleProperties;
    }

    @Bean
    public ServletRegistrationBean<WebServlet> h2Console(H2ConsoleProperties properties, ObjectProvider<DataSource> dataSource) {
        String path = properties.getPath();
        String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
        ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<>(new WebServlet(), urlMapping);
        H2ConsoleProperties.Settings settings = properties.getSettings();
        if (settings.isTrace()) {
            registration.addInitParameter("trace", "");
        }
        if (settings.isWebAllowOthers()) {
            registration.addInitParameter("webAllowOthers", "");
        }
        dataSource.ifAvailable((available) -> {
            try (Connection connection = available.getConnection()) {
                log.info("H2 console available at '" + path + "'. Database available at '"
                        + connection.getMetaData().getURL() + "'");
            } catch (Exception ex) {
                // Continue
            }
        });
        return registration;
    }

    @Bean
    public DataSource h2EmbedDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("org.h2.Driver");
        return dataSource;
    }
}
