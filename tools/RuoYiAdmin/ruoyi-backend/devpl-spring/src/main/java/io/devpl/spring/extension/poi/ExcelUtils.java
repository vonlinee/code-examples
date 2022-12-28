package io.devpl.spring.extension.poi;

import io.devpl.sdk.util.Validator;
import io.devpl.sdk.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * 一个Excel文件对应于一个workbook(HSSFWorkbook)， 一个workbook可以有多个sheet（HSSFSheet）组成，
 * 一个sheet是由多个row（HSSFRow）组成， 一个row是由多个cell（HSSFCell）组成
 * <a href="https://www.cnblogs.com/GarfieldEr007/p/14540744.html">...</a>
 * 总体思路如下：XLSX的文档读取类为XSSFWorkBook，其实现了WorkBook接口，可通过File类进行构造。然后通过Excel文档按照需求读取对应的工作表，
 * 再从工作表中根据所需行号等信息读取对应的行，最后根据列号定位到表中的单元格。该API可以从单元格中读取字符串、整形数、浮点数、日期和公式等数据
 */
public final class ExcelUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	private static final String EXCEL_2003L = ".xls"; // 2003- 版本的excel
	private static final String EXCEL_2007U = ".xlsx"; // 2007+ 版本的excel

	private ExcelUtils() {
	}

	/**
	 * @param fileName  Excel FilePath
	 * @param excelType Excel类型：XLSX，XLS
	 * @return if true success or false implies failure
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
		try {
			writeWorkbook(new HSSFWorkbook(), file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 根据文件后缀获取对应Workbook对象
	 * 
	 * @param filePath 文件全路径
	 * @return Workbook
	 */
	public static Workbook getWorkbook(String filePath) {
		String fileType = filePath.substring(filePath.lastIndexOf("."));
		Workbook workbook = null;
		try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
			File excelFile = new File(filePath);
			if (!excelFile.exists()) {
				return null;
			}
			workbook = createWorkbook(fileType, fileInputStream);
		} catch (IOException e) {
			logger.error("failed to get workbook by filepath: {}, cause: {}", filePath, e);
		}
		return workbook;
	}

	/**
	 * 根据输入流创建Workbook对象
	 * 
	 * @param fileType 文件类型 EXCEL_2003L、EXCEL_2007U
	 * @param is       输入流
	 * @return
	 */
	private static Workbook createWorkbook(String fileType, InputStream is) {
		Workbook workbook = null;
		try {
			if (EXCEL_2003L.equalsIgnoreCase(fileType)) {
				workbook = new HSSFWorkbook(is);
			} else if (EXCEL_2007U.equalsIgnoreCase(fileType)) {
				// if throw exception: java.lang.ClassNotFoundException:
				// org.apache.xerces.util.SecurityManager
				// add dependency of xerces to project:
				// https://mvnrepository.com/artifact/xerces/xercesImpl
				// implementation 'xerces:xercesImpl:2.12.2'
				// this exception won't be thrown but directyl log in console by the
				// Class[SaxHelper] in xmlbeans library.
				workbook = new XSSFWorkbook(is);
			} else {
				throw new UnsupportedOperationException(String.format("Unsupported Excel File AnyType[%s]", fileType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 获取扩展名
	 * 
	 * @param filepath
	 * @return
	 */
	private static String getExtension(String filepath) {
		File file = new File(filepath);
		String name = file.getName();
		int i = name.lastIndexOf("\\");
		if (i > 0) {
			name = name.substring(i + 1);
		}
		return name;
	}

	/**
	 * 批量读取文件夹下的所有EXCEL文件，非递归
	 * 
	 * @param filePath filepath
	 * @return
	 */
	public static List<List<Map<String, String>>> readFolder(String filePath) {
		File file = new File(filePath);
		List<List<Map<String, String>>> returnList = new ArrayList<>();
		if (file.exists() && file.isDirectory()) {
			File[] files = Validator.notNull(file.listFiles(
					(dir, name) -> dir.isFile() && (name.endsWith(EXCEL_2003L) || name.endsWith(EXCEL_2007U))));
			for (File file2 : files) {
				if (file2.isFile() && file2.exists()) {
					returnList.add(readExcel(file2.getAbsolutePath()));
				}
			}
		}
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
			workbook = getWorkbook(filePath);
			if (workbook == null) {
				return new ArrayList<>();
			}
			resultList = readExcel(workbook);
			return resultList;
		} catch (Exception e) {
			return new ArrayList<>();
		} finally {
			closeQuitely(workbook);
		}
	}

	/**
	 * 读取指定行
	 * 
	 * @param filePath 文件全路径
	 * @param rowNum   行号
	 * @return 一行的数据：Map<String, String>
	 */
	public static Map<String, String> readExcelRow(String filePath, int rowNum) {
		Workbook workbook = null;
		List<Map<String, String>> resultList;
		try {
			workbook = getWorkbook(filePath);
			if (workbook == null) {
				logger.info("获取workbook对象失败");
				return null;
			}
			resultList = readExcel(workbook);
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
			logger.error("failed to close workbook, casuse:", e);
		}
	}

	/**
	 * 解析Excel文件，返回数据对象
	 * 
	 * @param workbook
	 * @return List<Map<String, String>> 默认List第一个元素为表头，其余为表数据
	 */
	public static List<Map<String, String>> readExcel(Workbook workbook) {
		List<Map<String, String>> dataList = new ArrayList<>();
		int sheetCount = workbook.getNumberOfSheets();// 获取一个Excel中sheet数量
		for (int i = 0; i < sheetCount; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			// 如果前面有空行，则第一行为不是空行的第一行 同时Excel中行号从1开始，但是这里行号从0开始
			// 获取第一行的序号，如果第一行不为空的数据的行号为8，则结果为7
			int firstRowCount = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowCount);
			// 同理：最后一列表示最后一列不为空的列的序号，这里都是从0开始
			short firstCellNum = firstRow.getFirstCellNum(); // 实际列号-1
			int cellCount = firstRow.getPhysicalNumberOfCells();// 获取实际的列数
			
			Map<String, String> title = new HashMap<>();
			List<String> mapKey = new ArrayList<>();
			// 获取表头信息，放在List中备用，默认第一行为表头
			for (int i1 = firstCellNum, k1 = firstCellNum + cellCount; i1 < k1; i1++) {
				String value = firstRow.getCell(i1).toString();
				mapKey.add(firstRow.getCell(i1).toString());
				title.put(value, value);
			}
			dataList.add(0, title); // 表头
			// 解析每一行数据，构成数据对象
			int rowStart = firstRowCount + 1; // 默认第一行为表头，因此+1跳过第一行
			// 获取实际的行数，并非最后一行的行号
			int rowCount = sheet.getPhysicalNumberOfRows();
			for (int j = rowStart, k = rowStart + rowCount; j < k; j++) {
				Row row = sheet.getRow(j);// 获取对应的row对象
				if (row == null)
					continue;
				// 将每一行数据转化为一个Map对象
				dataList.add(readRow(row, firstCellNum, cellCount, mapKey));
			}
		}
		return dataList;
	}

	/**
	 * 将每一行数据转化为一个Map对象
	 * @param row            行对象
	 * @param startColumnNum 开始的列号(对于整个表格不在Excel左上角的情况)
	 * @param cellCount      列数
	 * @param mapKey         表头Map
	 * @return
	 */
	public static Map<String, String> readRow(Row row, int startColumnNum, int cellCount, List<String> mapKey) {
		Map<String, String> resultMap = new HashMap<>();
		if (mapKey == null || mapKey.isEmpty()) {
			// 无表头
			return resultMap;
		}
		Cell cell = null;
		for (int i = startColumnNum, k1 = startColumnNum + cellCount; i < k1; i++) {
			cell = row.getCell(i);
			if (cell == null) {
				resultMap.put(mapKey.get(i - startColumnNum), "");
			} else {
				resultMap.put(mapKey.get(i - startColumnNum), getCellVal(cell));
			}
		}
		return resultMap;
	}

	/**
	 * 获取单元格的值
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
			throw e;
		}
	}

	public static CellStyle setCellStyle(Workbook wb) {
		CellStyle dataStyle = wb.createCellStyle();
		dataStyle.setAlignment(HorizontalAlignment.LEFT);
		// [org.apache.poi.ss.usermodel.VerticalAlignment], not
		// [org.apache.poi.sl.usermodel.VerticalAlignment]
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Font dataFont = wb.createFont();
		dataStyle.setFont(dataFont);
		// Border
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		return dataStyle;
	}
	
}
