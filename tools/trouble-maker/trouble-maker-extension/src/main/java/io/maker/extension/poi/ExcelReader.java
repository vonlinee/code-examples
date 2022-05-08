package io.maker.extension.poi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);

	private static final ExcelReader READER = new ExcelReader();

	/**
	 * 注意读取完毕后要关闭Workbook
	 * @param excelFilePath
	 * @return
	 */
	public List<ExcelSheet> readWorkbook(String excelFilePath) {
		try (Workbook workbook = WorkbookFactory.create(new File(excelFilePath))) {
			return readWorkbook(workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<ExcelSheet> readWorkbook(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		List<ExcelSheet> excelSheets = new ArrayList<>(numberOfSheets);
		for (int i = 0; i < numberOfSheets; i++) {
			excelSheets.add(readSheet(workbook, i));
		}
		return excelSheets;
	}

	public ExcelSheet readSheet(Workbook wb, int sheetIndex) {
		if (wb == null) {
			throw new RuntimeException("工作簿对象为空");
		}
		int sheetSize = wb.getNumberOfSheets();
		if (sheetIndex < 0 || sheetIndex > sheetSize - 1) {
			throw new RuntimeException("工作表获取错误");
		}
		return readSheet(wb.getSheetAt(sheetIndex));
	}

	public ExcelSheet readSheet(Sheet sheet) {
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName(sheet.getSheetName());
		List<Map<int[], Object>> rows = readRows(sheet);
		if (rows.isEmpty()) {
			return excelSheet;
		}
		Map<int[], Object> row1 = rows.get(0);
		for (Map.Entry<int[], Object> entry : row1.entrySet()) {
			excelSheet.addTitle((String) entry.getValue());
		}
		if (row1.size() != excelSheet.getTitles().size()) {
			LOGGER.info("表数据不标准!");
		}
		for (int i = 1, size = rows.size(); i < size; i++) {
			Map<int[], Object> row = rows.get(i);
			List<Object> list = new ArrayList<>();
			list.addAll(row.values());
			excelSheet.addRow(list);
		}
		return excelSheet;
	}

	/**
	 * 读取单个Excel表
	 * 
	 * @param sheet
	 * @return
	 */
	private List<Map<int[], Object>> readRows(Sheet sheet) {
		List<Map<int[], Object>> list = new ArrayList<>();
		// 如果开始行号和结束行号都是-1的话，则全表读取
		int physicalNumberOfRows = sheet.getPhysicalNumberOfRows(); // 实际存在的行数
		int lastRowNum = sheet.getLastRowNum(); // 最后一行有元素的行号，以0开始
		if (physicalNumberOfRows != lastRowNum + 1) { // physicalNumberOfRows <= lastRowNum + 1
			System.out.println("该文档不规则");
		}
		int firstRowNum = sheet.getFirstRowNum(); // 开始有元素的行号，以0开始
		MapRowProcessor processor = new MapRowProcessor();
		for (int i = firstRowNum; i < lastRowNum + 1; i++) {
			// System.out.println("Excel第" + (i + 1) + "行"); // Excel的行数从1开始
			Row row = sheet.getRow(i);
			if (row == null) {
				System.out.println("Excel第" + (i + 1) + "行为空，直接跳过");
				continue;
			}
			list.add(processor.process(row));
		}
		return list;
	}
}