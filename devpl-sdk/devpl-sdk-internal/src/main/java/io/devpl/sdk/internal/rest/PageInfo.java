package io.devpl.sdk.internal.rest;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageInfo implements Serializable {

    private static final long serialVersionUID = -9005418320425464234L;

    private int total;
    private int totalPageNum;
    private int currentPage;
    private int rowCount;
}