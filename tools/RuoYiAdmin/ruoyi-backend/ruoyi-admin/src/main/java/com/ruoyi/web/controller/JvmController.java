package com.ruoyi.web.controller;

import com.ruoyi.common.utils.SpringUtils;
import io.devpl.sdk.rest.ResultTemplate;
import io.devpl.sdk.rest.Results;
import io.devpl.sdk.rest.Status;
import io.devpl.spring.web.mvc.RequestInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/jvm/")
public class JvmController {

    @GetMapping("/springbean")
    public ResultTemplate getSpringBeans(RequestInfo param) {
        Object bean = SpringUtils.getBean(JvmController.class);
        return Results.builder().status(Status.HTTP_200).data(bean).build();
    }
}
