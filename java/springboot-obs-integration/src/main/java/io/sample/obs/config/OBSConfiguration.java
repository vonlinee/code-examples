package io.sample.obs.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.util.StringUtils;

import com.obs.services.ObsClient;

/**
 * 华为云OBS配置
 */
@Configuration
@ConditionalOnProperty(name = "obs.enable", havingValue = "true")
public class OBSConfiguration implements ApplicationListener<ContextClosedEvent> {

	private static final Logger log = LoggerFactory.getLogger(OBSConfiguration.class);
	
    /**
     * obs.enable=true的情况下创建OBS客户端
     * 建议整个代码工程全局使用一个ObsClient客户端，只在程序初始化时创建一次，因为创建多个ObsClient客户端在高并发场景下会影响性能。
     * 在使用临时aksk时，aksk会有过期时间，可调用ObsClient.refresh("yourAccessKey", "yourSecretKey", "yourSecurityToken")刷新ObsClient的aksk，不必重新创建ObsClient。
     * ObsClient是线程安全的，可在并发场景下使用。
     * ObsClient在调用ObsClient.close方法关闭后不能再次使用，保证全局使用一个ObsClient客户端的情况下，不建议主动关闭ObsClient客户端。
     * 官方文档：<a href="https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0202.html">...</a>
     * @param properties 密钥、终端节点等配置属性
     */
    @Bean
    public ObsService obsService(OBSProperties properties) {
        // 详细配置
        ObsClient client = new ObsClient(properties.getAccessKey(), properties.getSecretKey(), properties.getEndpoint());
        // 检查桶是否已创建，未创建的进行创建
        if (!StringUtils.hasText(properties.getBucketName())) {
            log.error("未指定使用的桶名称");
        }
        if (!client.headBucket(properties.getBucketName())) {
            log.info("OBS客户端初始化成功失败，桶{}不存在，请在控制台创建桶", properties.getBucketName());
            return null;
        }
        log.info("OBS客户端初始化成功");
        return new HuaweiObsService(client);
    }

    /**
     * 系统关闭时，关闭OBS客户端链接
     * @param contextClosedEvent 上下文关闭事件
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        log.info("系统关闭，关闭OBS客户端");
        ApplicationContext context = contextClosedEvent.getApplicationContext();
        ObsClient client = context.getBean(ObsClient.class);
        try {
            client.close();
        } catch (IOException ioException) {
            log.error("OBS客户端关闭异常", ioException);
        }
    }
}
