package org.setamv.shardingsphere.sample.dynamic.model;

import lombok.Data;

/**
 * 系统配置
 *
 * @author setamv
 * @date 2021-04-16
 */
@Data
public class SysConfig extends NoTenantBaseEntity {

    private Long configId;
    private String configCode;
    private String configValue;
    private String configDesc;
}
