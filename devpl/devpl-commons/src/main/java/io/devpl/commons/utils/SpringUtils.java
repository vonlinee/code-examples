package io.devpl.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * ApplicationContextAware: Context refresh之后才会进行注入
 */
public final class SpringUtils implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SpringUtils.class);

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (log.isDebugEnabled()) {
            log.info("注入Spring上下文 => {}", applicationContext);
        }
        context = applicationContext;
    }

    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {
        return context.getBeansOfType(type);
    }
}
