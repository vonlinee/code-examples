package org.setamv.shardingsphere.sample.dynamic;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动类
 * @author setamv
 * @date 20210415
 */
@SpringBootApplication
public class DynamicDataSourceSampleApplication
{

    public static void main( String[] args )
    {
        new SpringApplicationBuilder(DynamicDataSourceSampleApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
