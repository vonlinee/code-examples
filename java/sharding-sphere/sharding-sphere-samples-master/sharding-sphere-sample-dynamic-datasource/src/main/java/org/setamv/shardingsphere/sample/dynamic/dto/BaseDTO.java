package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO基类
 *
 * @author setamv
 * @date 2021-04-15
 */
@Data
public class BaseDTO implements Serializable {

    private Long tenantId;

    /**
     * 分页查询参数。如果没有值，将部分也查询
     */
    private Integer pageNum;
    private Integer pageSize;
}
