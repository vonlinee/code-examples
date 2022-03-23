package io.maker.extension.poi;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * N行1列数据
 */
public class ExcelRow {

    private Map<String, Object> rowData;

    private int columnCount;

    public ExcelRow(Map<String, Object> rowData) {
        this.rowData = rowData;
    }

    public ExcelRow() {
        this.rowData = new LinkedHashMap<>();
    }

    public ExcelRow(int columnCount) {
        this.columnCount = columnCount;
        this.rowData = new LinkedHashMap<>(columnCount);
    }

    public void addColumn(String columnName, Object columnValue) {
        this.rowData.put(columnName, columnValue);
    }

    public Map<String, Object> getRowData() {
        return rowData;
    }

    public void setRowData(Map<String, Object> rowData) {
        this.rowData = rowData;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}
