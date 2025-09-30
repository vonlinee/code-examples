package org.lancoo.crm.service;

import cn.hutool.extra.spring.SpringUtil;
import com.huaweicloud.sdk.ces.v1.model.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.lancoo.crm.entity.ResourceInfo;
import org.lancoo.crm.entity.ResourceMetricInfo;
import org.lancoo.crm.feign.ApiFeignClient;
import org.lancoo.crm.utils.DateTimeUtils;
import org.lancoo.crm.utils.Utils;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DataPullTask implements Runnable {

    @NotNull
    ResourceInfo resourceInfo;
    ApiFeignClient client;
    ResourceMetricService resourceMetricService;

    public DataPullTask(@NotNull ResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
        this.client = SpringUtil.getBean(ApiFeignClient.class);
        this.resourceMetricService = SpringUtil.getBean(ResourceMetricService.class);
    }

    /**
     * <a href="https://support.huaweicloud.com/api-ces/ces_03_0034.html#section5">...</a>
     */
    @Override
    public void run() {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start("参数封装");
        BatchListMetricDataRequest request = new BatchListMetricDataRequest();
        // 请求参数
        BatchListMetricDataRequestBody body = new BatchListMetricDataRequestBody();

        List<MetricInfo> metricInfoList = new ArrayList<>();

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setNamespace("AGT.ECS");
        metricInfo.setMetricName("disk_usedPercent"); // 磁盘使用率

        List<MetricsDimension> dimensions = new ArrayList<>();
        MetricsDimension dimension = new MetricsDimension();
        dimension.setName("instance_id");
        dimension.setValue(resourceInfo.getResourceId());

        dimensions.add(dimension);
        metricInfo.setDimensions(dimensions);

        metricInfoList.add(metricInfo);
        body.setMetrics(metricInfoList);

//        "1"，原始数据
//        "300"，5分钟粒度
//        "1200"，20分钟粒度
//        "3600"，1小时粒度
//        "14400"，4小时粒度
//        "86400"，1天粒度
        body.setPeriod("1");
        body.setFilter("average");
        LocalDateTime now = LocalDateTime.now();
        body.setFrom(now.toEpochSecond(ZoneOffset.UTC));
        body.setTo(now.plusMinutes(1).toEpochSecond(ZoneOffset.UTC));
        request.setBody(body);
        stopWatch.stop();

        stopWatch.start("HTTP请求");
        BatchListMetricDataResponse response = client.batchQueryMetricData(resourceInfo.getProjectId(), request);
        stopWatch.stop();

        // 从响应中提取结果
        stopWatch.start("计算");
        List<ResourceMetricInfo> resourceMetricInfoList = getMetricData(response);
        stopWatch.stop();


        stopWatch.start("存库");
        LocalDateTime localDateTime = LocalDateTime.now();

        final long timestamp = localDateTime.toEpochSecond(ZoneOffset.UTC);
        String parse = DateTimeUtils.toString(localDateTime);

        for (ResourceMetricInfo resourceMetricInfo : resourceMetricInfoList) {
            resourceMetricInfo.setResourceId(resourceInfo.getId());
            resourceMetricInfo.setProjectId(resourceInfo.getProjectId());
            resourceMetricInfo.setCreateTimestamp(timestamp);
            resourceMetricInfo.setCreateTime(parse);
        }
        resourceMetricService.batchInsert(resourceMetricInfoList);
        stopWatch.stop();

        String message = Thread.currentThread().getName() +
                         resourceInfo.getId() + " " + resourceInfo.getResourceName()
                         + " insert " + resourceMetricInfoList.size() +
                         " rows, cost " + Utils.convertToReadableTime(stopWatch.getTotalTimeMillis(), TimeUnit.MILLISECONDS)

                         + "\n" + stopWatch.prettyPrint();
        System.out.println(message);
    }


    private List<ResourceMetricInfo> getMetricData(BatchListMetricDataResponse response) {
        // 转换结果
        List<ResourceMetricInfo> resourceMetricInfoList = resourceMetricService.generateTestData(2000);

        for (ResourceMetricInfo resourceMetricInfo : resourceMetricInfoList) {
            resourceMetricInfo.setCreateTimestamp(System.currentTimeMillis());
        }

        return resourceMetricInfoList;
    }
}
