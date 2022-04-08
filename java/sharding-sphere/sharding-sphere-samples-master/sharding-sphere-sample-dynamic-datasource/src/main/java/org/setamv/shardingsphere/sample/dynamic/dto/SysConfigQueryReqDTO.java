package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.util.Collection;

/**
 * 系统配置查询参数DTO
 *
 * @author setamv
 * @date 2021-04-17
 */
@Data
public class SysConfigQueryReqDTO extends BaseDTO {

    private Long configId;
    private String configCode;
    private String configValue;
    private String configDesc;
    private Collection<String> configCodes;
}
