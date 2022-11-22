package io.sample.obs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 华为云对象存储服务OBS配置属性，Spring自动从配置文件中注入
 */
@Data
@ConfigurationProperties(prefix = "obs")
public class OBSProperties {

    // 是否使用OBS存储
    private boolean enable;

    // access key
    private String accessKey;

    // secret key
    private String secretKey;

    // 区域，https://support.huaweicloud.com/productdesc-obs/obs_03_0148.html
    private String region;

    // 终端节点，https://support.huaweicloud.com/productdesc-obs/obs_03_0152.html
    private String endpoint;

    // 桶名称
    private String bucketName;

    // 封面60（1分钟）、文档3600（1小时）、视频43200（12小时）、压缩包86400（24小时）临时URL默认有效时长，单位秒
    private long coverUrlExpireSeconds;
    private long docUrlExpireSeconds;
    private long videoExpireSeconds;
    private long compressExpireSeconds;

    // 没有明确指定过期时长的默认值，单位秒，30分钟（过期时间是必选值）
    private long defaultExpireSeconds = 1800;
}
