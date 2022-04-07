package sample.dynamic.datasource.controller;

import org.apache.ibatis.binding.MapperProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.dynamic.datasource.mapper.BusinessMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
        return businessMapper.multiJoinQuery();
    }
}
