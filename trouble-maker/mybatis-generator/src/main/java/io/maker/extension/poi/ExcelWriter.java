package io.maker.extension.poi;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 模板
 */
public abstract class ExcelWriter {

    public abstract Workbook create(File file) throws IOException;

    public abstract void fill(Workbook workbook, List<Map<String, Object>> rows);

    public abstract void write(Workbook workbook, File file) throws IOException;

<<<<<<< HEAD
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
=======
    public final void write(List<Map<String, Object>> rows, File file) {
        try {
            Workbook workbook = create(file);
            fill(workbook, rows);
            write(workbook, file);
>>>>>>> 91afff9bc4bd2868a8cbda20a5b003e0aa7e9efe
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
