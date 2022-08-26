package io.devpl.sdk.support.spring;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DevplPropertyFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

        String encoding = resource.getEncoding();

        Map<String, Object> propsMap = new HashMap<>();

        MapPropertySource propertySource = new MapPropertySource("devpl-props", propsMap);

        return propertySource;
    }
}
