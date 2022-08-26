package io.devpl.sdk.api.controller;

import io.devpl.sdk.internal.rest.ListResult;
import io.devpl.sdk.api.entity.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/devpl/api/internal/test")
public class TestController {

    @PostMapping("/1")
    public ListResult<Model> test(Map<String, Object> paramMap) {
        List<Model> modelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            modelList.add(new Model(i, "model-" + i));
        }
        ListResult<Model> result = ListResult.<Model>builder()
                .data(modelList)
                .build();
        result.setCode(200);
        result.setMessage("查询成功");
        result.setDescription("描述信息");
        return result;
    }
}
