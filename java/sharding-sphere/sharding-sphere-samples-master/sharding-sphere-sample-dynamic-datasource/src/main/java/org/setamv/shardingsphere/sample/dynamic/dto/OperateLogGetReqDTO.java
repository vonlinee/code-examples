package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志查询参数DTO
 *
 * @author setamv
 * @date 2021-04-17
 */
@Data
public class OperateLogGetReqDTO implements Serializable {

    private Long id;
    private LocalDateTime operateTime;
}
