package com.panemu.tiwulfx.common;

import java.io.Serializable;
import java.util.List;

public class TableData<T> implements Serializable {

	private List<T> rows;
	private boolean moreRows;
	private long totalRows;

	public TableData() {
	}

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

}
