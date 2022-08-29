package io.devpl.sdk.api.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test", url = "www.httpbin.org")
public interface RemoteFeignClient {

    @GetMapping("/")
    String test();
}
