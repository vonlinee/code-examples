package io.devpl.sdk.api.controller;

import feign.ReflectiveFeign;
import feign.Target;
import io.devpl.sdk.api.service.HttpbinService;
import io.devpl.sdk.api.service.InternalConfigService;
import io.devpl.sdk.api.service.remote.RemoteFeignClient;
import io.devpl.sdk.internal.rest.ListResult;
import io.devpl.sdk.api.entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/devpl/api/internal/test")
public class TestController {

    @Resource
    RemoteFeignClient feignClient;

    @Autowired
    InternalConfigService internalConfigService;

    @Autowired
    HttpbinService httpbinService;

    @PostMapping("/1")
    public ListResult<Model> test(Map<String, Object> paramMap) {
        List<Model> modelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            modelList.add(new Model(i, "model-" + i));
        }

        return null;
    }

    @PostMapping("/testfeign")
    public Object test1(Map<String, Object> paramMap) {

        if (feignClient instanceof Target.HardCodedTarget) {
            Target.HardCodedTarget target = (Target.HardCodedTarget) this.feignClient;
        }
        String test = feignClient.test();
        // FeignInvocationHandler
        Target.HardCodedTarget<String> target;
        return test;
    }

    @PostMapping("/testhttpbin")
    public Object httpbinTest1(Map<String, Object> paramMap) {
        ReflectiveFeign feign;
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        return httpbinService.get();
    }
}
