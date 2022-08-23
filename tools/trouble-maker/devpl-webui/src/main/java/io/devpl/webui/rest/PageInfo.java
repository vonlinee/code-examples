package io.devpl.webui.rest;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页信息封装，用于ListResultTemplate
 */
@Data
public class PageInfo {

    private int pageIndex = 0;

    private int pageSize = -1;
    //总记录数
    protected long total = 0;
}
