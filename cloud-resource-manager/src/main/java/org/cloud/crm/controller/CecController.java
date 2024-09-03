package org.cloud.crm.controller;

import org.cloud.crm.entity.CpuInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * <a href="https://support.huaweicloud.com/api-ces/ces_03_0033.html">...</a>
 */
@RestController
@RequestMapping("/api/")
public class CecController {

    /**
     * GET /V1.0/{project_id}/metric-data?namespace={namespace}&metric_name=
     * {metric_name}&dim.{i}=key,value&from={from}&to={to}&period={period}&filter={filter}
     *
     * @return 获取指标数据
     */
    @GetMapping("/V1.0/{project_id}/metric-data")
    public List<CpuInfo> getMetricData(@PathVariable(value = "project_id") String projectId,
                                       @RequestParam("namespace") String namespace,
                                       @RequestParam("metric_name") String metricName,
                                       Long from, Long to,
                                       String period,
                                       String filter) {


        return Collections.emptyList();
    }
}