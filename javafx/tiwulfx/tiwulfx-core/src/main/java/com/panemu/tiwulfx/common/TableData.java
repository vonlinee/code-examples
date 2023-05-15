package com.panemu.tiwulfx.common;

import java.io.Serializable;
import java.util.List;

/**
 * 表格数据
 * @param <T> 表格数据类型
 */
public class TableData<T> implements Serializable {

    private List<T> rows;
    private boolean moreRows;
    private long totalRows;

    public TableData(List<T> rows, boolean moreRows, long totalRows) {
        this.rows = rows;
        this.moreRows = moreRows;
        this.totalRows = totalRows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public boolean isMoreRows() {
        return moreRows;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setHasMoreRows(boolean moreRows) {
        this.moreRows = moreRows;
    }

    public boolean hasMoreRows() {
        return moreRows;
    }
}
