package io.devpl.sdk.support.spring;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ToString
@ConfigurationProperties(prefix = "devpl")
public class DevplProperties implements InitializingBean {

    private boolean enabled;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }
}
