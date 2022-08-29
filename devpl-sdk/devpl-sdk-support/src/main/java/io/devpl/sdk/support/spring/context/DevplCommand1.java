package io.devpl.sdk.support.spring.context;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class DevplCommand1 implements ApplicationRunner, Ordered {

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner执行");
        System.out.println(args);
    }
}
