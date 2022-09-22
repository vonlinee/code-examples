package io.devpl.configured;

import io.devpl.configured.entity.ConfigLoadTask;
import io.devpl.spring.SpringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 配置数据加载：从数据库加载配置数据
 */
@Component
public class ConfigDataLoader implements CommandLineRunner {

    private static String TASK_INIT = "devpl.config.load.init-sql";

    @Override
    public void run(String... args) throws Exception {
        JdbcTemplate template = SpringUtils.getBean(JdbcTemplate.class);
        String sql = SpringUtils.getRequiredProperty(TASK_INIT);

        template.execute(sql, (CallableStatementCallback<ConfigLoadTask>) cs -> {
            ConfigLoadTask task = new ConfigLoadTask();
            task.setTaskId(cs.getInt("TASK_ID"));
            return task;
        });
    }
}