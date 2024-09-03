package org.example.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MyTask implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(this::process,
                triggerContext -> {
                    // 每次执行定时任务时都会执行此方法
                    String cron = "*/2 * * * * ?";
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                });
    }

    // 定时任务逻辑
    private void process() {
        System.out.println("task is running");
    }
}