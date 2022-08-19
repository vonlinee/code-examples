package org.example.springboot.controller;

import javax.annotation.Resource;

import org.example.springboot.feign.InternalFeignClient;
import org.example.springboot.serialize.SerializableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@Resource
	InternalFeignClient feignClient;
	
	@Value("${server.port}")
	private int port;
	
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/serialize", method = RequestMethod.GET)
    @ResponseBody
    public SerializableModel serialize() {
    	SerializableModel localModel = new SerializableModel();
    	System.out.println("发送方: " + port);
    	
    	localModel.setAge(30);
    	localModel.setName("孙允珠");
    	
    	// 通过网络序列化传输
    	// 对面会将数据原样返回
        SerializableModel remoteModel = feignClient.serialzie(localModel);
        
        System.out.println(localModel);
        System.out.println(remoteModel);
        
        return remoteModel;
    }
}


// HttpTracerFilter