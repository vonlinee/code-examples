package ioc.programing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooConfiguration {

    private final FooRepository repository;

    public FooConfiguration(FooRepository repository) {
        this.repository = repository;
    }

    @Bean("fooService-1")
    public FooService fooService() {
        return new FooService(this.repository);
    }
}