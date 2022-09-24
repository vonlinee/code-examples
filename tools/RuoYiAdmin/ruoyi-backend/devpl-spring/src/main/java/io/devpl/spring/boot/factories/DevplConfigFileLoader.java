package io.devpl.spring.boot.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * 加载自定义的配置文件，并将其放到Spring的Environment中进行管理
 * 加载自定义配置文件
 * https://blog.csdn.net/WayneLee0809/article/details/108194131
 * https://juejin.cn/post/6874426451043876871
 *
 * @see org.springframework.boot.context.config.ConfigFileApplicationListener
 */
@Slf4j
public class DevplConfigFileLoader implements EnvironmentPostProcessor {

    // 配置文件名称匹配
    private ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver();

    public DevplConfigFileLoader() {
        super();
    }

    /**
     * Environment的后置处理
     *
     * @param environment
     * @param application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        loadDevplConfigFiles(environment, application);
    }

    private void loadDevplConfigFiles(ConfigurableEnvironment environment, SpringApplication application) {
        List<PropertySourceLoader> loaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, application.getClassLoader());
        for (PropertySourceLoader loader : loaders) {
            if (!(loader instanceof MultitypePropertySourceLoader)) {
                continue;
            }
            try {
                ClassPathResource resource = new ClassPathResource("devpl.properties");
                List<PropertySource<?>> propertySources = loader.load(resource.getFilename(), resource);
                if (!CollectionUtils.isEmpty(propertySources)) {
                    System.out.println("加载配置文件：" + propertySources);
                    propertySources.forEach(environment.getPropertySources()::addLast);
                }
            } catch (IOException e) {
                System.out.println("配置文件加载失败：" + e.getMessage());
            }
            break;
        }
    }
}
