package io.devpl.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

/**
 * 通用/常用的配置项
 * 通过Spring@ConfigurationProperties和@EnableConfigurationProperties注解自动注入，配置项前缀为config
 */
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class ConfigProperties {

    // 公开可访问附件上传文件夹
    private String publicAttachmentSaveDir;

    // 私有附件上传文件夹
    private String privateAttachmentSaveDir;

    // 临时文件夹（兼容OBS时使用）
    private String tempDir;

    // 分块大小
    private DataSize uploadChunkSize;

    // 基础平台地址
    private String baseAddr;

    /**
     * 获取基础平台地址，确保不以/结尾
     */
    public String getBaseAddr() {
        if (this.baseAddr != null && this.baseAddr.endsWith("/"))
            return this.baseAddr.substring(0, this.baseAddr.length() - 1);
        return baseAddr;
    }

    // 本系统地址
    private String addr;

    // 系统ID
    private String sysId;

    // 用户名密码登录时密码的加密算法
    public static final String ENCRYPT_ALGORITHM = "md5";

    // 加密次数
    public static final Integer HASH_ITERATIONS = 1;

    // 工作流待分配的审核任务查询参数，与绘制流程图时指定的candidateUsers一致
    public static final String UNASSIGNED_TASK_QUERY_STRING = "UNASSIGNED_TASK";

    // 为了在审查任务列表中查询待完成的管理端任务，排除机构用户任务，统一使用该名称作为任务名称，绘制流程图时指定任务的Name
    public static final String ADMIN_TASK_NAME = "管理审核";

    // JWT Token过期时间，默认10分钟，单位毫秒
    // public static final long JWT_EXPIRE_TIME = 10 * 60 * 1000;

    // 课室的监控IP地址、用户名、密码是否为必填项，true必须，默认false
    private boolean classroomCctvEnable = false;
}
