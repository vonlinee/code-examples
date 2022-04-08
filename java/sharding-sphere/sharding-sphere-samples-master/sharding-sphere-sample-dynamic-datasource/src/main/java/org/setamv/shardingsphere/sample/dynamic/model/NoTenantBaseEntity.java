package org.setamv.shardingsphere.sample.dynamic.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @author setamv
 * @date 2021-04-15
 */
@Data
public class NoTenantBaseEntity implements Serializable {

    private Long createUserId;
    private LocalDateTime createTime;
    private Long updateUserId;
    private LocalDateTime updateTime;
}
