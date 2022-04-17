package sample.spring.config.annotation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component // 或者@Configuration
@ConfigurationProperties(prefix = "person")
@Data
public class Person {
    private Map<String, String> maps;
    private List<String> list;
    private String name;
}