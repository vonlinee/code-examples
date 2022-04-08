package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志查询参数DTO
 *
 * @author setamv
 * @date 2021-04-17
 */
@Data
public class OperateLogQueryReqDTO extends BaseDTO {
    private Long id;
    private LocalDateTime operateTime;
    private String logContent;
    private LocalDateTime startOperateTime;
    private LocalDateTime endOperateTime;
}
