package io.devpl.sdk.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient(url = "www.httpbin.org", name = "www.httpbin.org")
public interface HttpbinService {

    @RequestMapping("/get")
    Map<String, Object> get();
}
