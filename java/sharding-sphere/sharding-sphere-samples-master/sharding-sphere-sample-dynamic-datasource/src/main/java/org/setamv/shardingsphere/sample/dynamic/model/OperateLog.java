package org.setamv.shardingsphere.sample.dynamic.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author setamv
 * @date 2021-04-16
 */
@Data
public class OperateLog extends NoTenantBaseEntity {

    private Long id;
    private LocalDateTime operateTime;
    private String logContent;
    private Long operatorId;
}
