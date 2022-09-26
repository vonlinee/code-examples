package io.devpl.configured;

import io.devpl.configured.entity.ConfigLoadTask;
import io.devpl.configured.repository.ConfigLoadTaskRepository;
import io.devpl.spring.context.SpringContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * 配置数据加载：从数据库加载配置数据
 */
@Component
public class ConfigDataLoader implements CommandLineRunner {

    @Resource
    private ConfigLoadTaskRepository loadTaskRepository;

    private static final String TASK_INIT_SQL = "select * from t_config_load_task";

    @Override
    public void run(String... args) throws Exception {
        List<ConfigLoadTask> taskList = loadTaskRepository.findAll();
        // 排序
        taskList.sort(Comparator.comparing(ConfigLoadTask::getOrderNum));

        JdbcTemplate template = SpringContext.getBean(JdbcTemplate.class);
        for (ConfigLoadTask configLoadTask : taskList) {
            if (!configLoadTask.isEnable()) continue;
            String querySql = configLoadTask.getQuerySql();
            template.execute(querySql);
        }
    }
}