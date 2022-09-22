package com.ruoyi.framework.configured;

import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConfigDataLoader implements CommandLineRunner {

    private static String TASK_INIT = "devpl.config.load.init-sql";

    @Override
    public void run(String... args) throws Exception {
        JdbcTemplate template = SpringUtils.getBean(JdbcTemplate.class);
        String sql = SpringUtils.getRequiredProperty(TASK_INIT);
        System.out.println(sql);
    }
}
