package io.maker.extension.poi;

import io.maker.base.lang.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelWriter {

    public static final String DEFAULT_SHEET_NAME = "sheet-1";

    public static final String XLSX_EXCEL = "xlsx";
    public static final String XLS_EXCEL = "xls";

    public boolean write(File file, String[][] data, String excelType, String sheetName) {
        if (XLSX_EXCEL.equalsIgnoreCase(excelType)) {
            return writeXlsx(file, data, sheetName);
        }
        throw new UnsupportedOperationException("不支持的Excel类型:" + excelType);
    }

    /**
     * 不同数据类型的写入方法
     * @param file
     * @param table
     * @return
     */
    public boolean write(File file, ExcelTable table) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet(table.getSheetName());
        List<ExcelColumn<String>> columnList = table.getColumnList();
        //表头
        Row titleRow = sheet.createRow(0);
        for (int j = 0; j < columnCount; j++) {
            Cell cell = titleRow.createCell(j);
            cell.setCellValue(columnList.get(j).getTitle());
        }
        //表数据
        for (int i = 1; i < rowCount; i++) {
            System.out.println("\n写入第 " + i + "行：");
            Row dataRow = sheet.createRow(i);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = dataRow.createCell(j);
                String cellValue = columnList.get(j).get(i - 1);
                System.out.printf("%s\t", cellValue);
                cell.setCellValue(cellValue);
            }
        }
        return writeToWorkbook(book, file);
    }

    public boolean write(File file, String[][] data, String sheetName) {
        return writeXlsx(file, data, sheetName);
    }

    public boolean write(File file, String[][] data) {
        return writeXlsx(file, data, DEFAULT_SHEET_NAME);
    }

    public static void write(File file, List<Map<String, Object>> rowList) {

    }

    public boolean write(File file, List<List<Pair<String, String>>> tableData, String sheetName) {
        if (tableData == null || tableData.isEmpty()) {
            throw new RuntimeException("data cannot be null");
        }
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);
        int rowCount = tableData.size();
        // 表头
        List<Pair<String, String>> title = tableData.get(0);
        int columnCount = title.size();
        Row titleRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            Cell cell = titleRow.createCell(i + 1);
            cell.setCellValue(title.get(i).getValue());
        }
        // 表数据
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row currentRow = sheet.createRow(rowNum);
            // currentRow.createCell(0).setCellFormula("ROW() - 1");
            for (int colNum = 0; colNum < columnCount; colNum++) {
                Cell cell = currentRow.createCell(0);
                cell.setCellValue(tableData.get(rowNum).get(colNum).getValue());
            }
        }
        return writeToWorkbook(book, file);
    }

    private boolean writeXlsx(File file, String[][] data, String sheetName) {
        if (data == null) {
            throw new RuntimeException("data cannot be null");
        }
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);
        int rowCount = data.length;
        int columnCount = data[0].length;
        // 表头
        String[] title = data[0];
        Row titleRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            Cell cell = titleRow.createCell(i + 1);
            cell.setCellValue(title[i]);
        }
        // 表数据
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row currentRow = sheet.createRow(rowNum);
            // currentRow.createCell(0).setCellFormula("ROW() - 1");
            for (int colNum = 0; colNum < columnCount; colNum++) {
                Cell cell = currentRow.createCell(0);
                cell.setCellValue(data[rowNum][colNum]);
            }
        }
        return writeToWorkbook(book, file);
    }

    private boolean writeToWorkbook(Workbook workbook, File file) {
        boolean result = false;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean writeAndClose(Workbook workbook, File file) {
        boolean result = false;
        if (file.exists()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                result = true;
            } catch (IOException e) {
                try {
                    workbook.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return result;
    }
}
