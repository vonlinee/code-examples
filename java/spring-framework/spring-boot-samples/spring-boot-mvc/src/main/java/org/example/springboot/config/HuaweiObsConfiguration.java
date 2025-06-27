package org.example.springboot.config;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.fs.NewBucketRequest;
import com.obs.services.model.fs.ObsFSBucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HuaweiObsConfiguration {

//    @Bean
//    public ObsClient obsClient() {
//        ObsConfiguration config = new ObsConfiguration();
//        ObsClient client = new ObsClient(config);
//        ObsFSBucket bucket = client.newBucket(new NewBucketRequest());
//        System.out.println(bucket.getLocation());
//        return client;
//    }
}
