package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.service.AutoCompleteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("/api/ac")
public class AutoCompleteController {

    /**
     * 获取MyBatis XML映射文件名称
     * @param mapperLocationPrefix
     * @param searchKey
     * @return
     */
    @GetMapping("/mapperxml")
    public Result<?> getAllMapperXmlNames(String mapperLocationPrefix, String searchKey) {
        return Results.of(Set.of("mapping"));
    }
}
