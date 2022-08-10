package sample.spring.transaction.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

@Component("spring-utils")
public class SpringUtils implements ApplicationContextAware, EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtils.class);

    //non-static
    private ApplicationContext applicationContext;
    private Environment environment;

    private SpringUtils() {
    }

    private static SpringUtils utils; //singleton instance

    public static <T> T getBean(String name, Class<T> requiredType) {
        return utils.getApplicationContext().getBean(name, requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        LOGGER.info("inject ApplicationContext {} to SpringUtils: {}", applicationContext, this);
        utils = applicationContext.getBean(SpringUtils.class);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return utils.getApplicationContext().getBean(requiredType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) utils.getApplicationContext().getBean(beanName);
    }

    public static String findConfigXml(Class<?> clazz, String filename) {
        return Objects.requireNonNull(clazz.getResource(filename)).toExternalForm();
    }
}