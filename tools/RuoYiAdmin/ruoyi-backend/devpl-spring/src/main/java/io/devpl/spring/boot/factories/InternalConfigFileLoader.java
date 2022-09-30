package io.devpl.spring.boot.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.*;

/**
 * 优先级最高
 * 使用代码的方式进行加载，比较灵活，不使用注解的方式
 * 加载自定义的配置文件，并将其放到Spring的Environment中进行管理
 * https://blog.csdn.net/WayneLee0809/article/details/108194131
 * https://juejin.cn/post/6874426451043876871
 * @see org.springframework.boot.context.config.ConfigFileApplicationListener
 */
public class InternalConfigFileLoader implements SpringApplicationRunListener, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(InternalConfigFileLoader.class);

    /**
     * 自定义的配置文件名匹配模式
     */
    private final List<String> patterns = List.of("classpath*:devpl*.properties", "classpath*:devpl*.yml", "classpath*:devpl*.xml");

    private SpringApplication application;
    private String[] args;

    public InternalConfigFileLoader(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        loadDevplConfigFiles(environment, application);
    }

    /**
     * 加载Devpl自定义的配置文件
     * @param environment ConfigurableEnvironment
     * @param application SpringApplication
     */
    private void loadDevplConfigFiles(ConfigurableEnvironment environment, SpringApplication application) {
        // 用于加载配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 获取所有的 PropertySourceLoader 实例
        List<PropertySource<?>> propertySources = new ArrayList<>();
        List<PropertySourceLoader> loaders = SpringFactoriesLoader
                .loadFactories(PropertySourceLoader.class, application.getClassLoader());

        Set<Resource> resourcesFinded = new LinkedHashSet<>();
        for (String pattern : patterns) {
            Resource[] resources;
            try {
                resources = resolver.getResources(pattern);
            } catch (IOException exception) {
                LOG.error("failed to load config files with pattern {}, cause", pattern, exception);
                resources = new Resource[0];
            }
            resourcesFinded.addAll(Arrays.asList(resources));
        }
        List<String> toBeLoadedResouces = new ArrayList<>(resourcesFinded.size());
        for (Resource resource : resourcesFinded) {
            String filename = resource.getFilename();
            toBeLoadedResouces.add(filename);
            for (PropertySourceLoader loader : loaders) {
                if (!support(loader.getFileExtensions(), filename)) {
                    continue;
                }
                try {
                    propertySources.addAll(loader.load(resource.getFilename(), resource));
                } catch (IOException exception) {
                    LOG.error("failed to load config file {}, exception: {}", resource.getFilename(), exception);
                    continue;
                }
                // 加载成功
                toBeLoadedResouces.remove(filename);
                LOG.info("PropertySourceLoader {} load {} successfully", loader.getClass().getName(), filename);
            }
        }
        for (String toBeLoadedResouce : toBeLoadedResouces) {
            LOG.warn("PropertySourceLoader load {} failed", toBeLoadedResouce);
        }
        // 去重
        Set<PropertySource<?>> distinctPropertySource = new HashSet<>(propertySources);
        distinctPropertySource.forEach(environment.getPropertySources()::addLast);
    }

    private boolean support(String[] extensions, String filename) {
        // Arrays.asList(loader.getFileExtensions())
        for (String fileExtension : extensions) {
            if (filename.endsWith(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
