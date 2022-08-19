package org.example.springboot.feign;

import org.example.springboot.serialize.SerializableModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "a", url = "localhost:8080", path = "/test/")
public interface InternalFeignClient {

	@PostMapping("/serialzie")
	SerializableModel serialzie(SerializableModel model);
}
