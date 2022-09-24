package io.devpl.spring.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Properties;

/**
 * 定义创建资源的PropertySource包装器的策略接口
 * 属于Spring容器创建前，需要先预加载一些属性配置，交由Spring的配置管理器管理，可使用@Value读取。
 * PropertySourceFactory主要用来扩展Spring对资源文件的读取，例如：配置文件等。
 */
@Slf4j
public class DevplPropertyFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(
            @Nullable String name, EncodedResource resource) throws IOException {

        String encoding = "UTF-8";
        if (StringUtils.hasLength(resource.getEncoding())) {
            encoding = resource.getEncoding();
        }
        Resource devplResource = resource.getResource();
        log.info("加载配置文件{}", resource.getResource().getFilename());
        if (StringUtils.hasLength(devplResource.getFilename())) {
            name = devplResource.getFilename();
        }
        if (!devplResource.exists()) {
            return new MapPropertySource(name, new LinkedHashMap<>());
        }
        Properties properties = new Properties();
        properties.load(devplResource.getInputStream());
        return new PropertiesPropertySource(name, properties);
    }


    public PropertySource<?> test1(String name, EncodedResource resource) throws IOException {
        // 请配置注解参数：ignoreResourceNotFound = true
        try {
            String filename = Objects.requireNonNull(resource.getResource().getFilename());
            //1.创建yaml文件解析工厂
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            //2.设置资源内容
            yaml.setResources(resource.getResource());
            //3.解析成properties文件
            Properties properties = yaml.getObject();
            if (properties == null) {
                throw new FileNotFoundException();
            }
            //4.返回符合spring的PropertySource对象
            return name != null ? new PropertiesPropertySource(name, properties)
                    : new PropertiesPropertySource(filename, properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Yaml解析异常，异常原因：" + e.getMessage());
        }
    }
}
