package io.maker.extension.poi;

import java.util.ArrayList;
import java.util.List;

import io.maker.base.lang.ValueWrapper;

/**
 * Excel Sheet表
 */
public class ExcelSheet {

	private String sheetName;
	private List<String> titles = new ArrayList<>();
	private List<List<Object>> data = new ArrayList<>();

	private int rowCount;
	private int colCount;
	
	public void addRow(List<Object> rowData) {
		this.data.add(rowData);
		rowCount ++;
	}

	public void addTitle(String title) {
		this.titles.add(title);
		rowCount ++;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 是否是一般的Excel表
	 * @return
	 */
	public boolean isNormal() {
		return hasData() && hasTitle() && data.get(0).size() == titles.size();
	}

	public boolean hasData() {
		return data.isEmpty();
	}

	public boolean hasTitle() {
		return titles.isEmpty();
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

	/**
	 * 获取指定列的数据
	 * @param columnNum
	 * @return
	 */
	public List<ValueWrapper> getColumnData(int columnNum) {
		rangeCheck(columnNum);
		List<ValueWrapper> columnValue = new ArrayList<>();
		columnValue.add(0, ValueWrapper.wrap(titles.get(columnNum)));
		int dataRowCount = data.size();
		for (int i = 0; i < dataRowCount; i++) {
			columnValue.add(i + 1, ValueWrapper.wrap(data.get(i).get(columnNum)));
		}
		return columnValue;
	}
	
	/**
	 * 范围检查
	 * @param column
	 */
	private void rangeCheck(int column) {
		if (column < 0 || column > titles.size()) {
			throw new IllegalArgumentException("column=" + column + ", totalColumnCount=" + titles.size());
		}
	}
}
