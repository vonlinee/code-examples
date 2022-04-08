package org.setamv.shardingsphere.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@SpringBootApplication
@MapperScan("org.setamv.shardingsphere.starter.dao")
public class DynamicConfigSampleApplication
{
    public static void main( String[] args )
    {
        new SpringApplicationBuilder(DynamicConfigSampleApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
