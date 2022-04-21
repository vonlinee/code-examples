package io.maker.extension.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.maker.base.io.FileUtils;
import io.maker.base.utils.Assert;
import io.maker.base.utils.StringUtils;
import io.maker.base.utils.Validator;

/**
 * 一个Excel文件对应于一个workbook(HSSFWorkbook)， 一个workbook可以有多个sheet（HSSFSheet）组成，
 * 一个sheet是由多个row（HSSFRow）组成， 一个row是由多个cell（HSSFCell）组成
 * https://www.cnblogs.com/GarfieldEr007/p/14540744.html
 * 总体思路如下：XLSX的文档读取类为XSSFWorkBook，其实现了WorkBook接口，可通过File类进行构造。然后通过Excel文档按照需求读取对应的工作表，
 * 再从工作表中根据所需行号等信息读取对应的行，最后根据列号定位到表中的单元格。该API可以从单元格中读取字符串、整形数、浮点数、日期和公式等数据
 */
public final class ExcelUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	private ExcelUtils() {
	}

	/**
	 * TODO
	 * @param fileName
	 * @param excelType
	 * @return
	 */
	public static boolean createEmptyExcel(String fileName, String excelType) {
		Validator.whenNull(fileName, "fileName cannot be null!");
		Validator.whenNull(excelType, "excelType cannot be null!");
		if (!fileName.endsWith(excelType)) {
			int i = fileName.lastIndexOf(".");
			if (i > 0) {
				fileName = fileName.substring(0, i) + excelType;
			}
		}
		File file = new File(fileName);
		if (!file.exists()) {
			FileUtils.createNewEmptyFile(file);
		}
		return true;
	}

	private static final String XLS = ".xls";
	private static final String XLSX = ".xlsx";

	/**
	 * 根据文件后缀获取对应Workbook对象
	 * 
	 * @param filePath
	 * @param fileType
	 * @return
	 */
	public static Workbook getWorkbook(String filePath, String fileType) {
		Workbook workbook = null;
		FileInputStream fileInputStream = null;
		try {
			File excelFile = new File(filePath);
			if (!excelFile.exists()) {
				logger.info(filePath + "文件不存在");
				return null;
			}
			fileInputStream = new FileInputStream(excelFile);
			if (fileType.equalsIgnoreCase(XLS)) {
				workbook = new HSSFWorkbook(fileInputStream);
			} else if (fileType.equalsIgnoreCase(XLSX)) {
				workbook = new XSSFWorkbook(fileInputStream);
			}
		} catch (Exception e) {
			logger.error("获取文件失败", e);
		} finally {
			try {
				if (null != fileInputStream) {
					fileInputStream.close();
				}
			} catch (Exception e) {
				logger.error("关闭数据流出错！错误信息：", e);
				return null;
			}
		}
		return workbook;
	}

	/**
	 * 批量读取文件夹下的所有EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<List<Map<String, String>>> readFolder(String filePath) {
		int fileNum = 0;
		File file = new File(filePath);
		List<List<Map<String, String>>> returnList = new ArrayList<>();
		List<Map<String, String>> resultList;
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isFile()) {
					resultList = readExcel(file2.getAbsolutePath());
					returnList.add(resultList);
					fileNum++;
				}
			}
		} else {
			logger.info("文件夹不存在");
			return returnList;
		}
		logger.info("共有文件：" + fileNum);
		return returnList;
	}

	/**
	 * 读取单个Excel文件，返回数据对象
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<Map<String, String>> readExcel(String filePath) {
		Workbook workbook = null;
		List<Map<String, String>> resultList;
		try {
			String fileType = filePath.substring(filePath.lastIndexOf("."));
			if (StringUtils.isEmpty(fileType))
				fileType = "xlsx";
			workbook = getWorkbook(filePath, fileType);
			if (workbook == null) {
				logger.info("获取workbook对象失败");
				return new ArrayList<>();
			}
			resultList = analysisExcel(workbook);
			return resultList;
		} catch (Exception e) {
			logger.error("读取Excel文件失败" + filePath + "错误信息", e);
			return new ArrayList<>();
		} finally {
			closeQuitely(workbook);
		}
	}

	/**
	 * 读取指定行
	 * 
	 * @param filePath
	 * @param rowNum
	 * @return
	 */
	public static Map<String, String> readExcelRow(String filePath, int rowNum) {
		Workbook workbook = null;
		List<Map<String, String>> resultList;
		try {
			String fileType = filePath.substring(filePath.lastIndexOf("."));
			if (StringUtils.isEmpty(fileType))
				fileType = "xlsx";
			workbook = getWorkbook(filePath, fileType);
			if (workbook == null) {
				logger.info("获取workbook对象失败");
				return null;
			}
			resultList = analysisExcel(workbook);
		} catch (Exception e) {
			logger.error("读取Excel文件失败" + filePath + "错误信息", e);
			return null;
		} finally {
			closeQuitely(workbook);
		}
		return null;
	}

	/**
	 * 关闭Excel对象
	 * 
	 * @param workbook
	 */
	public static void closeQuitely(Workbook workbook) {
		try {
			if (null != workbook) {
				workbook.close();
			}
		} catch (Exception e) {
			logger.error("关闭数据流出错！错误信息：", e);
		}
	}

	/**
	 * 解析Excel文件，返回数据对象
	 * 
	 * @param workbook
	 * @return
	 */
	public static List<Map<String, String>> analysisExcel(Workbook workbook) {
		List<Map<String, String>> dataList = new ArrayList<>();
		int sheetCount = workbook.getNumberOfSheets();// 或取一个Excel中sheet数量
		for (int i = 0; i < sheetCount; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			int firstRowCount = sheet.getFirstRowNum();// 获取第一行的序号
			Row firstRow = sheet.getRow(firstRowCount);
			int cellCount = firstRow.getLastCellNum();// 获取列数
			List<String> mapKey = new ArrayList<>();
			// 获取表头信息，放在List中备用
			for (int i1 = 0; i1 < cellCount; i1++) {
				mapKey.add(firstRow.getCell(i1).toString());
			}
			// 解析每一行数据，构成数据对象
			int rowStart = firstRowCount + 1;
			int rowEnd = sheet.getPhysicalNumberOfRows();
			for (int j = rowStart; j < rowEnd; j++) {
				Row row = sheet.getRow(j);// 获取对应的row对象
				if (row == null) {
					continue;
				}
				// 将每一行数据转化为一个Map对象
				Map<String, String> dataMap = convertRowToData(row, cellCount, mapKey);
				dataList.add(dataMap);
			}
		}
		return dataList;
	}

	/**
	 * 将每一行数据转化为一个Map对象
	 * 
	 * @param row       行对象
	 * @param cellCount 列数
	 * @param mapKey    表头Map
	 * @return
	 */
	public static Map<String, String> convertRowToData(Row row, int cellCount, List<String> mapKey) {
		if (mapKey == null) {
			logger.info("没有表头信息");
			return null;
		}
		Map<String, String> resultMap = new HashMap<>();
		Cell cell = null;
		for (int i = 0; i < cellCount; i++) {
			cell = row.getCell(i);
			if (cell == null) {
				resultMap.put(mapKey.get(i), "");
			} else {
				resultMap.put(mapKey.get(i), getCellVal(cell));
			}
		}
		return resultMap;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cel
	 * @return
	 */
	public static String getCellVal(Cell cel) {
		if (cel.getCellType() == CellType.STRING) {
			return cel.getRichStringCellValue().getString();
		}
		if (cel.getCellType() == CellType.NUMERIC) {
			return cel.getNumericCellValue() + "";
		}
		if (cel.getCellType() == CellType.BOOLEAN) {
			return cel.getBooleanCellValue() + "";
		}
		if (cel.getCellType() == CellType.FORMULA) { // 公式
			return cel.getCellFormula() + "";
		}
		return cel.toString();
	}

	/**
	 * 将 List<Map<String,Object>> 类型的数据导出为 Excel 默认 Excel 文件的输出路径为 项目根目录下 文件名为
	 * filename + 时间戳 + .xlsx
	 * 
	 * @param mapList  数据源(通常为数据库查询数据)
	 * @param filename 文件名前缀, 实际文件名后会加上日期
	 * @param title    表格首行标题
	 */
	public static void writeExcel(List<Map<String, Object>> mapList, String filename, String title) throws IOException {
		// 获取数据源的 key, 用于获取列数及设置标题
		Map<String, Object> map = mapList.get(0);
		Set<String> stringSet = map.keySet();
		ArrayList<String> headList = new ArrayList<>(stringSet);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(title); // 创建一个Sheet页
		// 为有数据的每列设置列宽
		for (int i = 0; i < headList.size(); i++) {
			sheet.setColumnWidth(i, 4000);
		}
		// //设置单元格字体样式
		// XSSFFont font = wb.createFont();
		// font.setFontName("等线");
		// font.setFontHeightInPoints((short) 16);
		// 创建单元格文字居中样式并设置标题单元格居中
		// XSSFCellStyle cellStyle = wb.createCellStyle();
		// cellStyle.setAlignment(HorizontalAlignment.CENTER);
		// 在sheet里创建第一行，并设置单元格内容为 title (标题)
		// if (!Validator.isNullOrEmpty(title)) {
		// XSSFRow titleRow = sheet.createRow(0);
		// XSSFCell titleCell = titleRow.createCell(0);
		// titleCell.setCellValue(title);
		// // titleCell.setCellStyle(cellStyle);
		// }
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headList.size() - 1));
		// 获得表格第二行
		XSSFRow row = sheet.createRow(1);
		// 根据数据源信息给第二行每一列设置标题
		for (int i = 0; i < headList.size(); i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(headList.get(i));
		}
		XSSFRow rows;
		XSSFCell cells;
		// 循环拿到的数据给所有行每一列设置对应的值
		for (int i = 0; i < mapList.size(); i++) {
			// 在这个sheet页里创建一行
			rows = sheet.createRow(i + 2);
			// 给该行数据赋值
			for (int j = 0; j < headList.size(); j++) {
				Object nullableValue = mapList.get(i).get(headList.get(j));
				String value = "NULL";
				if (nullableValue != null) {
					value = nullableValue.toString();
				}
				cells = rows.createCell(j);
				cells.setCellValue(value);
			}
		}
		// 使用项目根目录, 文件名加上时间戳
		writeWorkbook(wb, new File(filename), false);
	}

	private static void makeSureFileExists(File file) {
		if (!file.exists()) {
			try {
				Files.createFile(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建字符串的Excel表
	 * 
	 * @param workbook
	 * @param sheetName
	 * @param rowCount
	 * @param columnCount
	 * @return
	 */
	public static Sheet createSimpleSheet(Workbook workbook, String sheetName, int rowCount, int columnCount) {
		Sheet sheet = workbook.createSheet(sheetName);
		for (int i = 1; i <= rowCount; i++) {
			Row row = sheet.createRow(i);
			for (int j = 1; j <= columnCount; j++) {
				Cell cell = row.createCell(j, CellType.STRING);
				cell.setCellValue("");
			}
			row.setHeight((short) 10);
		}
		return sheet;
	}

	/**
	 * 将准备好的workbook文档输出到文件中
	 * 
	 * @param workbook    文档数据
	 * @param file        Excel文件
	 * @param fileExisted 文件是否存在
	 */
	public static void writeWorkbook(Workbook workbook, File file, boolean fileExisted) throws IOException {
		if (!fileExisted) {
			makeSureFileExists(file);
		}
		try (FileOutputStream fos = new FileOutputStream(file)) {
			workbook.write(fos);
			fos.flush();
		} catch (IOException e) {
			logger.error("failed to write workbook to {}, cause : {}", file.getAbsolutePath(), e.getMessage());
			throw e;
		}
	}
}
