package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询请求参数
 * @author setamv
 * @date 2021-04-17
 */
@Data
public class PageQueryReqDTO implements Serializable {

    private Integer pageNum;
    private Integer pageSize;

    public PageQueryReqDTO() {
        pageNum = 0;
        pageSize = 10;
    }
}
