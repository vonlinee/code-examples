package io.maker.extension.poi;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Iterator;
import java.util.List;

/**
 * 简单的Excel表格数据，数据必须从第一行第一列开始，且没有空行，空列
 * 表头不为空
 */
@Data
public class SimpleExcelTable {

    private int columnCount; //列数
    private int rowCount;  //行数
    private String[] titles;
    private List<String>[] columnDataList;

    /**
     * 将表格数据映射到SimpleExcelTable
     * @param workbook Excel Workbook
     * @return SimpleExcelTable
     */
    public static SimpleExcelTable read(Workbook workbook) {
        SimpleExcelTable excelTable = new SimpleExcelTable();
        Sheet sheet = workbook.getSheetAt(0);
        int rowStart = sheet.getFirstRowNum() + 1;
        int rowEnd = sheet.getLastRowNum();
        for (int i = rowStart; i <= rowEnd; i++) {
            Row row = sheet.getRow(i);
            Iterator<Cell> iterator = row.iterator();
            while (iterator.hasNext()) {
                Cell cell = iterator.next();
                String value = cell.getStringCellValue();
            }
        }
        return excelTable;
    }
}
