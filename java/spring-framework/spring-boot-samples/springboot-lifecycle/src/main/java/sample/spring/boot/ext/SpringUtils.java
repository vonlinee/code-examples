package sample.spring.boot.ext;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements EnvironmentAware {
    @Override
    public void setEnvironment(Environment environment) {

    }
}
