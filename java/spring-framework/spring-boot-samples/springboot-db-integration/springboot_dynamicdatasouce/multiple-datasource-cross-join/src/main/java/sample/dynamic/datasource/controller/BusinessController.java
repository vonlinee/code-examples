package sample.dynamic.datasource.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.dynamic.datasource.config.DataSourceDecision;
import sample.dynamic.datasource.config.DynamicDataSource;
import sample.dynamic.datasource.mapper.BusinessMapper;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    BusinessMapper businessMapper;
    // localhost:8888/business/multijoinquery

    /**
     * 跨库JOIN查询，查视图库
     * @return
     */
    @GetMapping("/multijoinquery")
    public List<Map<String, Object>> multiJoinQuery() {
        // MapperMethodProxy
    	DataSourceDecision.decide("business");
        return businessMapper.multiJoinQuery();
    }
}
