package io.devpl.sdk.support.spring.context;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class DevplCommand implements CommandLineRunner, Ordered {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner 执行");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
