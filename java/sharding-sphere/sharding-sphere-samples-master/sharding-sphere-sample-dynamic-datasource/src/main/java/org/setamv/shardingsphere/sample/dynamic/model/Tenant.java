package org.setamv.shardingsphere.sample.dynamic.model;

import lombok.Data;
import org.setamv.shardingsphere.sample.dynamic.enums.TenantStatusEnum;

/**
 * 租户
 *
 * @author setamv
 * @date 2021-04-15
 */
@Data
public class Tenant extends BaseEntity {

    private String tenantName;
    private TenantStatusEnum status;

}
