package io.maker.extension.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * 用于写简单的EXCEL文件，单元格都是字符串，不涉及其他样式
 * 基于Apache POI
 */
public class SimpleExcelWriter extends ExcelWriter {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static final String DEFAULT_SHEET_NAME = "sheet-1";

    public static final String XLSX_EXCEL = "xlsx";
    public static final String XLS_EXCEL = "xls";

    /**
     * 从第一行开始写数据
     * @param sheetName
     * @param rows      默认第一行为标题栏
     */
    private Workbook createSimpleWorkbook(String sheetName, List<Map<String, Object>> rows) {
        if (rows.size() == 0) {
            logger.error("数据为空!");
        }
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);
        for (int i = 0; i < rows.size(); i++) {
            addAndFillOneRow(sheet, i, rows.get(i));
        }
        return book;
    }

    /**
     * 从第一行开始写数据
     * @param sheetName
     * @param title
     * @param rows
     */
    private Workbook createSimpleWorkbook(String sheetName, Map<String, Object> title, List<Map<String, Object>> rows) {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);
        // 填充表头
        if (!title.isEmpty()) {
            addAndFillOneRow(sheet, 0, title);
        }
        // 表数据
        for (int rowNum = 1; rowNum < rows.size(); rowNum++) {
            addAndFillOneRow(sheet, rowNum, rows.get(rowNum));
        }
        return book;
    }

    /**
     * 加在指定的行数据
     * @param sheet
     * @param rowNum
     * @param rowData
     */
    private void addAndFillOneRow(Sheet sheet, int rowNum, Map<String, Object> rowData) {
        Row row = sheet.createRow(rowNum);
        int colCount = 1;
        for (String key : rowData.keySet()) {
            addCellForRow(row, colCount, rowData.get(key));
            colCount++;
        }
    }

    private void addCellForRow(Row row, int col, Object cellValue) {
        CellType cellType = null;
        if (cellValue == null) {
            cellType = CellType.STRING;
            Cell cell = row.createCell(col, cellType);
            cell.setCellValue("NULL");
        }
        if (cellValue instanceof Boolean) {
            cellType = CellType.BOOLEAN;
            Cell cell = row.createCell(col, cellType);
            cell.setCellValue((Boolean) cellValue);
        }
        if (cellValue instanceof String) {
            cellType = CellType.STRING;
            Cell cell = row.createCell(col, cellType);
            cell.setCellValue((String) cellValue);
        }
        if (cellValue instanceof Number) {
            cellType = CellType.NUMERIC;
            Cell cell = row.createCell(col, cellType);
            cell.setCellValue((Double) cellValue);
        }
    }

    private void writeAndClose(Workbook workbook, File file) {
        ensureFileExists(file);
        try {
            write(workbook, file);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            closeWorkBookQuitely(workbook);
        }
    }

    private void ensureFileExists(File file) {
        if (file != null && !file.exists()) {
            logger.info("文件 {} 不存在，准备新建该文件", file.getAbsolutePath());
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeWorkBookQuitely(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Workbook create(File file) {
        return null;
    }

    @Override
    public void fill(Workbook workbook, List<Map<String, Object>> rows) {

    }

    /**
     * 将准备好的workbook文档输出到文件中
     * @param workbook workbook
     * @param file     file
     */
    @Override
    public void write(Workbook workbook, File file) throws IOException {
        try (OutputStream stream = new FileOutputStream(file)) {
            workbook.write(stream);
        } catch (IOException e) {
            logger.error("failed to write workbook to {}, cause : {}", file.getAbsolutePath(), e.getMessage());
            throw e;
        }
    }

    public void write(File file, List<Map<String, Object>> rows, String sheetName) throws IOException {
        ensureFileExists(file);
        if (!file.getName().endsWith(".xlsx")) {
            logger.error("目标文件 {} 不是Excel文件", file.getAbsolutePath());
            return;
        }
        if (rows == null || rows.isEmpty()) {
            throw new RuntimeException("data cannot be null");
        }
        write(createSimpleWorkbook(sheetName, rows), file);
    }

    public void write(File file, List<Map<String, Object>> rows) throws IOException {
        write(file, rows, "Sheet-1");
    }
}
