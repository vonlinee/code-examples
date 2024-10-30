package org.lancoo.crm.controller;

import com.huaweicloud.sdk.ces.v1.model.BatchListMetricDataRequest;
import com.huaweicloud.sdk.ces.v1.model.BatchListMetricDataResponse;
import com.huaweicloud.sdk.ces.v1.model.BatchMetricData;
import org.lancoo.crm.domain.ServerResponse;
import org.lancoo.crm.entity.ProjectInfo;
import org.lancoo.crm.entity.ResourceMetricInfo;
import org.lancoo.crm.feign.ApiFeignClient;
import org.lancoo.crm.repository.ProjectInfoRepository;
import org.lancoo.crm.repository.ResourceMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/V1.0/")
public class CloudCesController {

    @Autowired
    private ResourceMetricRepository serverRepository;
    @Autowired
    private ProjectInfoRepository projectInfoRepository;

    @Resource
    private ApiFeignClient apiFeignClient;

    @GetMapping("/getAllServers")
    public Page<ResourceMetricInfo> getAllServers(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Double maxCpuUsage,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (status != null) {
            return serverRepository.findByStatus(status, pageable);
        } else if (region != null) {
            return serverRepository.findByRegion(region, pageable);
        } else if (maxCpuUsage != null) {
            return serverRepository.findByCpuUsageLessThan(maxCpuUsage, pageable);
        }
        return serverRepository.findAll(pageable);
    }

    @GetMapping("/getAllServersByHttp")
    public ServerResponse getAllServers1(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Double maxCpuUsage,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return apiFeignClient.getAllServers(page, size);
    }

    @PostMapping("/{project_id}/batch-query-metric-data")
    public BatchListMetricDataResponse batchQueryMetricData(@PathVariable(name = "project_id") String projectId, @RequestBody BatchListMetricDataRequest request) {
        long projectIdValue = Long.parseLong(projectId);

        BatchListMetricDataResponse response = new BatchListMetricDataResponse();

        response.setHttpStatusCode(200);

        List<BatchMetricData> metricDataList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            BatchMetricData bmd = new BatchMetricData();
            bmd.setUnit("ms");

            metricDataList.add(bmd);
        }

        response.setMetrics(metricDataList);
        return response;
    }
}