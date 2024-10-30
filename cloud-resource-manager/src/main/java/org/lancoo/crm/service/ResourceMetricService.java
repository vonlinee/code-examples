package org.lancoo.crm.service;

import org.lancoo.crm.entity.ResourceMetricInfo;
import org.lancoo.crm.entity.ServerStatus;
import org.lancoo.crm.repository.ResourceMetricRepository;
import org.lancoo.crm.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ResourceMetricService {

    @Autowired
    private ResourceMetricRepository resourceMetricRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final Random random = new Random();

    /**
     * 生成测试数据
     */
    public List<ResourceMetricInfo> generateTestData(int count) {
        List<ResourceMetricInfo> testData = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ResourceMetricInfo server = new ResourceMetricInfo();
            server.setName("Server-" + i);
            server.setResourceId(randomLong(1, 20));
            server.setProjectId(randomLong(1, 4));
            server.setStatus(randomStatus());
            server.setRegion(randomRegion());
            server.setCpuUsage(random.nextDouble() * 100); // 0 - 100%
            server.setDiskUsage(random.nextDouble() * 100); // 0 - 100%
            server.setMemoryUsage(random.nextDouble() * 100); // 0 - 100%
            server.setInboundTraffic(random.nextDouble() * 1000); // 0 - 1000 MB
            server.setOutboundTraffic(random.nextDouble() * 1000); // 0 - 1000 MB
            server.setIoUtilization(random.nextDouble() * 100); // 0 - 100%
            server.setTemperature(20 + random.nextDouble() * 30); // 20 - 50°C
            server.setUptime(randomUptime());
            server.setInstanceType(randomInstanceType());
            server.setCreateTime(DateTimeUtils.nowTime());
            testData.add(server);
        }
        return testData;
    }

    @Transactional
    public void batchInsert(List<ResourceMetricInfo> servers) {
        int batchSize = 50; // 每批处理的大小
        for (int i = 0; i < servers.size(); i++) {
            entityManager.persist(servers.get(i));
            if (i % batchSize == 0 && i > 0) {
                entityManager.flush(); // 执行插入操作
                entityManager.clear(); // 清除上下文
            }
        }
        // 处理剩余的实体
        entityManager.flush();
        entityManager.clear();
    }

    private ServerStatus randomStatus() {
        return random.nextBoolean() ? ServerStatus.RUNNING : ServerStatus.STOPPED;
    }

    private String randomRegion() {
        String[] regions = {"us-west", "us-east", "eu-central", "eu-west", "ap-south"};
        return regions[random.nextInt(regions.length)];
    }

    private long randomLong(int min, int max) {
        // 生成指定范围内的随机整数
        return (long) (Math.random() * (max - min + 1)) + min;
    }

    private String randomUptime() {
        return random.nextInt(30) + " days"; // 0 - 29 days
    }

    private String randomInstanceType() {
        String[] instanceTypes = {"t2.micro", "m5.large", "c5.xlarge", "t3.medium"};
        return instanceTypes[random.nextInt(instanceTypes.length)];
    }
}