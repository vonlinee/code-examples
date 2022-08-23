package io.devpl.sdk.internal.rest;

import java.io.Serializable;

public class PageInfo implements Serializable {
    private static final long serialVersionUID = -9005418320425464234L;
    private int total;
    private int allPage;
    private int currentPage;
    private int rowCount;

    public PageInfo() {
        this.total = 0;
    }

    public PageInfo(int currentPage, int rowCount) {
        this.currentPage = currentPage;
        this.rowCount = rowCount;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAllPage() {
        return this.allPage;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}