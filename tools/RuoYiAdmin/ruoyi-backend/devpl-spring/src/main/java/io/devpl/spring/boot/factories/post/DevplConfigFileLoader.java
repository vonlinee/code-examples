package io.devpl.spring.boot.factories.post;

import io.devpl.spring.boot.factories.environment.MultitypePropertySourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * 加载自定义的配置文件，并将其放到Spring的Environment中进行管理
 * 加载自定义配置文件
 * https://blog.csdn.net/WayneLee0809/article/details/108194131
 * https://juejin.cn/post/6874426451043876871
 * @see org.springframework.boot.context.config.ConfigFileApplicationListener
 */
@Slf4j
public class DevplConfigFileLoader implements EnvironmentPostProcessor {

    public DevplConfigFileLoader() {
        super();
    }

    /**
     * Environment的后置处理
     * @param environment
     * @param application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        loadDevplConfigFiles(environment, application);
    }

    /**
     * 加载Devpl自定义的配置文件
     * @param environment ConfigurableEnvironment
     * @param application SpringApplication
     */
    private void loadDevplConfigFiles(ConfigurableEnvironment environment, SpringApplication application) {
        List<PropertySourceLoader> loaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, application.getClassLoader());
        for (PropertySourceLoader loader : loaders) {
            // 使用自定义的资源加载器进行加载
            if (!(loader instanceof MultitypePropertySourceLoader)) {
                continue;
            }

            String[] devplConfigFiles = {"devpl.properties", "devpl-jdbc.properties"};

            for (String devplConfigFile : devplConfigFiles) {
                ClassPathResource resource = new ClassPathResource(devplConfigFile);
                try {
                    List<PropertySource<?>> propertySources = loader.load(resource.getFilename(), resource);
                    if (!CollectionUtils.isEmpty(propertySources)) {
                        log.info("加载配置文件 {}", propertySources);
                        propertySources.forEach(environment.getPropertySources()::addLast);
                    }
                } catch (IOException e) {
                    log.error("加载配置文件失败 {}", resource);
                }
            }
            break;
        }
    }
}
