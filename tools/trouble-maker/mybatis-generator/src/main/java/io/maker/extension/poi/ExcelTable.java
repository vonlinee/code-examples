package io.maker.extension.poi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据全使用字符串
 */
public class ExcelTable implements Serializable {

    private String excelType;
    private String sheetName;
    private List<ExcelColumn<String>> columnList = new ArrayList<>();

    public ExcelTable(String excelType, String sheetName) {
        this.excelType = excelType;
        this.sheetName = sheetName;
    }

    public ExcelTable(String sheetName, String excelType, List<ExcelColumn<String>> columnList) {
        this.sheetName = sheetName;
        this.excelType = excelType;
        this.columnList.addAll(columnList);
    }

    public void addColumn(ExcelColumn<String> column) {
        if (column != null) {
            this.columnList.add(column);
        }
    }

    /**
     * 列添加新列，表第一个元素为表头
     * @param columnData 该列的数据
     */
    public void addColumn(List<String> columnData) {
        if (columnData != null && columnData.size() >= 1) {
            ExcelColumn<String> column = new ExcelColumn<>(columnData.get(0), String.class);
            this.columnList.add(column);
        }
    }

    public void addColumn(String title, List<String> columnData) {
        ExcelColumn<String> column = new ExcelColumn<>(title, String.class);
        this.columnList.add(column);
    }

    public ExcelColumn<String> getColumn(int columnIndex) {
        return columnList.get(columnIndex);
    }

    public void addRows(List<ExcelRow> excelRows) {
        if (excelRows == null || excelRows.isEmpty()) {
            return;
        }
        ExcelRow titleRow = excelRows.get(0);
        Map<String, Object> titleRowData = titleRow.getRowData();
        int columnCount = titleRowData.size();
        int rowCount = excelRows.size();
        Object[] titleNames = titleRowData.keySet().toArray();
        for (int colNum = 0; colNum < columnCount; colNum++) {
            String title = (String) titleNames[colNum];
            ExcelColumn<String> column = new ExcelColumn<>(title, String.class);
            for (int rowNum = 1; rowNum < rowCount; rowNum++) {
                ExcelRow excelRow = excelRows.get(rowNum);
                Map<String, Object> rowData = excelRow.getRowData();
                Object[] rowValues = rowData.keySet().toArray();
                column.add(rowValues[colNum]);
            }
            addColumn(column);
        }
    }

    public boolean isEmpty() {
        return columnList.isEmpty();
    }

    public int getRowCount() {
        if (columnList.size() == 0) return 0;
        return columnList.get(0).length();
    }

    public int getColumnCount() {
        return columnList.size();
    }

    public String getExcelType() {
        return excelType;
    }

    public void setExcelType(String excelType) {
        this.excelType = excelType;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<ExcelColumn<String>> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ExcelColumn<String>> columnList) {
        this.columnList = columnList;
    }
}
