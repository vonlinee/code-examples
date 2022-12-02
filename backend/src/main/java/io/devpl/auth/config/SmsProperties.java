package io.devpl.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短信服务配置
 */
@ConfigurationProperties(prefix = "sms")
@Data
public class SmsProperties {

    // 验证码过期时间，单位分钟，默认5分钟
    private long expireAfter = 5;

    // 验证码发送间隔，单位秒，默认60秒
    private long sendInterval = 60;

    // AccessKey
    private String accessKey;

    // Secret
    private String secret;

    // 验证码签名
    private String codeSign;

    // 验证码模板代码
    private String codeTemplate;

    // 投诉整改审核通过通知联系人短信签名
    private String complaintSign;

    // 投诉整改审核通过通知联系人短信模板
    private String complaintTemplate;
}
