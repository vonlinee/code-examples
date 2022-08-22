package io.devpl.webui.rest;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页信息封装，用于ListResultTemplate
 */
@Data
public class PageInfo implements Serializable {

    private int pageIndex = 0;

    private int pageSize = -1;

}
