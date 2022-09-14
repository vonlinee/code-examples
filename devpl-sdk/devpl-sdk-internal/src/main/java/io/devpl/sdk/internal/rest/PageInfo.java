package io.devpl.sdk.internal.rest;

import java.io.Serializable;

/**
 * 封装分页信息
 */
public class PageInfo implements Serializable {

    private static final long serialVersionUID = -9005418320425464234L;

    private int pageNum;
    private int pageSize;
    private int nextPage;
    private int firstRowNum;

}