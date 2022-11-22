package io.sample.obs.controller;

import io.sample.obs.config.ObsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/obs")
public class ObsController {

    @Resource
    ObsService obsService;

    @GetMapping("/signedUrl")
    public String getSignedUrl(@RequestParam Map<String, Object> param) {
        return obsService.getSingedUrl(String.valueOf(param.get("bucketName")), String.valueOf(param.get("objectKey")));
    }
}
