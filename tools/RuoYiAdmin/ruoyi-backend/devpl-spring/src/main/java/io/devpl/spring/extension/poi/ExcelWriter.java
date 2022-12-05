package io.devpl.spring.extension.poi;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ExcelWriter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWriter.class);

	public void write(ExcelSheet sheet, File file) {
		Workbook workbook = createWorkbook(file);
		if (workbook != null) {
			createSheet(workbook, sheet.getSheetName(), sheet.getTitles().toArray(new String[] {}), sheet.getData());
		}
		try (OutputStream os = new FileOutputStream(file)){
			writeIntoOutputStream(workbook, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeAndShow() {

	}

	/**
	 * 写常规的表格：数据从左上方开始，呈现矩形形状
	 * 
	 * @param workbook
	 */
	private void writeNormal(Workbook workbook, OutputStream os, String[] title, List<Object> data) {
		
	}

	private Workbook createWorkbook(File file) {
		try {
			Workbook workbook = null;
			if (!file.exists()) {
				workbook = createNewExcelFileWhenNotExists(file);
				if (!file.canRead()) {
					boolean ops = file.setReadable(true);
					if (!ops) {
						System.out.println("无法设为可读");
					}
				}
			}
			return workbook;
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 不存在时创建一个符合格式的Excel文件
	 * @param file
	 */
	private Workbook createNewExcelFileWhenNotExists(File file) {
		XSSFWorkbook workbook = null;
		try (FileOutputStream os = new FileOutputStream(file)) {
			workbook = new XSSFWorkbook();
			writeIntoOutputStream(workbook, os);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return workbook;
	}
	
	/**
	 * 创建表  只在io.maker.extension.poi.ExcelWriter.writeNormal(Workbook, OutputStream, String[], List<Object>)
	 * 
	 * @param workbook
	 * @param sheetName
	 * @param title
	 * @param data
	 * @return
	 */
	private Sheet createSheet(Workbook workbook, String sheetName, String[] title, List<List<Object>> data) {
		Sheet sheet = workbook.createSheet(sheetName);
		// 标题字体
		Font titleFont = workbook.createFont();
		titleFont.setFontName("simsun");
		titleFont.setBold(true);
		// titleFont.setFontHeightInPoints((short) 14);
		titleFont.setColor(IndexedColors.BLACK.index);
		// 单元格字体
		Font dataFont = workbook.createFont();
		dataFont.setFontName("simsun");
		dataFont.setColor(IndexedColors.BLACK.index);
		
		// 标题单元格样式
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		titleStyle.setFont(titleFont);
		setBorder(titleStyle, BorderStyle.THIN);
		
		// 普通格式单元格
		CellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setAlignment(HorizontalAlignment.LEFT);//左对齐
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dataStyle.setFont(dataFont);
		setBorder(dataStyle, BorderStyle.THIN);

		// 时间格式单元格
		CellStyle dataStyleDt = workbook.createCellStyle();
		dataStyleDt.setAlignment(HorizontalAlignment.LEFT);//左对齐
		dataStyleDt.setVerticalAlignment(VerticalAlignment.CENTER);
		dataStyleDt.setFont(dataFont);
		setBorder(dataStyleDt, BorderStyle.THIN);
		dataStyleDt.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd HH:mm:ss"));// 展示的格式
		
		// 标题
		Row titleRow = sheet.createRow(0);
		int columnCount = title.length;
		for (int i = 0; i < columnCount; i++) {
			createCell(titleRow, i, title[i], titleStyle);
		}
		// 数据
		int rowCount = data.size();
		// 
		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.createRow(i + 1); //第一行为标题行
			for (int j = 0; j < columnCount; j++) {
				createCell(row, j, data.get(i).get(j), dataStyle);
			}
		}
		return sheet;
	}

	private static void setBorder(CellStyle style, BorderStyle border) {
		style.setBorderTop(border);
		style.setBorderLeft(border);
		style.setBorderRight(border);
		style.setBorderBottom(border);
	}
	
	private Cell createCell(Row row, int column, Object value, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		if (value == null) {
			cell.setCellValue("NULL");
		} else {
			for (;;) {
				if (value instanceof String) {
					cell.setCellValue((String) value);
					break;
				}
				if (value instanceof Timestamp) {
					cell.setCellValue(((Timestamp) value).toLocalDateTime());
					break;
				}
				if (value instanceof LocalDateTime) {
					cell.setCellValue((LocalDateTime) value);
					break;
				}
				if (value instanceof Number) {
					cell.setCellValue((Double) value);
					break;
				}
				if (value instanceof Boolean) {
					cell.setCellValue((boolean) value);
					break;
				}
			}
		}
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		return cell;
	}

	/**
	 * 将workbook写入输入流中，不关闭流
	 * @param workbook
	 * @param os
	 * @throws IOException
	 */
	private void writeIntoOutputStream(Workbook workbook, OutputStream os) throws IOException {
		workbook.write(os);
		os.flush();
	}
}
