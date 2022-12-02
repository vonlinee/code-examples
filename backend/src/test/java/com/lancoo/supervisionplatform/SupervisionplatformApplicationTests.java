package com.lancoo.supervisionplatform;

import cn.hutool.extra.spring.SpringUtil;
import com.obs.services.ObsClient;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SupervisionplatformApplicationTests {

    @Test
    void contextLoads() {

        ObsClient obsClient = SpringUtil.getBean(ObsClient.class);

        ListObjectsRequest request = new ListObjectsRequest("smartedu-lg");
// 设置列举带有prefix前缀的100个对象
        request.setMaxKeys(100);
        request.setPrefix("/d071585cbdff6f71d816f18e297aeb24/");
        ObjectListing result = obsClient.listObjects(request);
        for(ObsObject obsObject : result.getObjects()){
            System.out.println("\t" + obsObject.getObjectKey());
            System.out.println("\t" + obsObject.getOwner());
        }
    }
}
