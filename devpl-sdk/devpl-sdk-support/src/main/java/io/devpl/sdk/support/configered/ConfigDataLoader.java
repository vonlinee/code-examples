package io.devpl.sdk.support.configered;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigDataLoader implements CommandLineRunner {

    private static String TASK_INIT = "devpl.config.load.init-sql";

    @Override
    public void run(String... args) throws Exception {


    }
}
