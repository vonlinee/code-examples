package org.lancoo.crm.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.lancoo.crm.entity.ProjectInfo;
import org.lancoo.crm.entity.ResourceInfo;
import org.lancoo.crm.feign.ApiFeignClient;
import org.lancoo.crm.repository.CronTaskRepository;
import org.lancoo.crm.repository.ProjectInfoRepository;
import org.lancoo.crm.repository.ResourceInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * https://support.huaweicloud.com/api-ces/ces_03_0034.html
 */
@Slf4j
@Service
public class DataPuller implements SchedulingConfigurer, CommandLineRunner {

    @Resource
    ApiFeignClient client;
    @Resource
    CronTaskRepository cronTaskRepository;
    @Resource
    ResourceMetricService resourceMetricService;
    @Resource
    ProjectInfoRepository projectInfoRepository;
    @Resource
    ResourceInfoRepository resourceInfoRepository;

    // 0 */1 * * * ?  每分钟
    // 0/2 * * * * ?  每 2 秒

    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar taskRegistrar) {
        List<ResourceInfo> resourceInfoList = resourceInfoRepository.findAll();
        log.info("注册任务数量 {}", resourceInfoList.size());
//        for (ResourceInfo resourceInfo : resourceInfoList) {
//            taskRegistrar.addTriggerTask(
//                    new DataPullTask(resourceInfo),
//                    // 每秒钟执行一次
//                    new CronTrigger("0 */1 * * * ?")
//            );
//        }
    }

    @Override
    public void run(String... args) throws Exception {
        long count = resourceInfoRepository.count();
        List<ResourceInfo> resourceInfos = new ArrayList<>();
        if (count == 0) {
            for (int i = 0; i < 100; i++) {
                ResourceInfo resourceInfo = new ResourceInfo();
                resourceInfo.setResourceName("Resource-" + i);
                resourceInfo.setResourceType("ECS");
                if (i % 3 == 0) {
                    resourceInfo.setProjectId(3L);
                } else if (i % 2 == 0) {
                    resourceInfo.setProjectId(2L);
                } else {
                    resourceInfo.setProjectId(1L);
                }
                resourceInfo.setResourceId(UUID.randomUUID().toString());
                resourceInfos.add(resourceInfo);
            }
        }
        resourceInfoRepository.saveAllAndFlush(resourceInfos);

        if (projectInfoRepository.count() == 0) {
            // 生成项目信息
            for (int i = 0; i < 3; i++) {
                ProjectInfo projectInfo = new ProjectInfo();
                projectInfo.setProjectName("Project-" + (i + 1));
                projectInfoRepository.save(projectInfo);
            }
        }
    }
}
