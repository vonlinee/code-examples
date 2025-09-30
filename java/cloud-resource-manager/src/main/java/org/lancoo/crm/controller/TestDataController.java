package org.lancoo.crm.controller;

import org.lancoo.crm.entity.ResourceMetricInfo;
import org.lancoo.crm.service.ResourceMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestDataController {

    @Autowired
    private ResourceMetricService resourceMetricService;


    @PostMapping("/generateTestData")
    public boolean generateTestData(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 200;
        }
        List<ResourceMetricInfo> resourceMetricInfoList = resourceMetricService.generateTestData(count);
        resourceMetricService.batchInsert(resourceMetricInfoList);
        return true;
    }
}