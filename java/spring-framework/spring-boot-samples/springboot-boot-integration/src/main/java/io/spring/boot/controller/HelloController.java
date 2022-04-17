package io.spring.boot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.spring.boot.util.RestClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    public String hello() {
        return "Hello Spring-Boot";
    }

    @RequestMapping("/info")
    public Map<String, String> getInfo(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String name1) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("name1", name1);
//		if(true)
//			throw new RuntimeException("错误");
        return map;
    }

    @RequestMapping("/list")
    public List<Map<String, String>> getList() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        for (int i = 1; i <= 5; i++) {
            map = new HashMap<>();
            map.put("name", "Shanhy-" + i);
            list.add(map);
        }

        return list;
    }

    /**
     * 测试 RestClient
     * @return
     * @author SHANHY
     * @create 2016年3月18日
     */
    @RequestMapping("/test1")
    public String testRestTemplate() {

        String url = "http://localhost:8080/myspringboot/hello/info.json?name={name}&name1={name1}";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "Tom");
        paramMap.put("name1", "Lily");
        String result = RestClient.getClient().getForObject(url, String.class, paramMap);
        System.out.println(">>>>>result>>>>>" + result);

        return result;
    }

}
