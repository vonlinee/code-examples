package io.maker.extension.poi;

import io.maker.base.io.FileUtils;
import io.maker.base.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 一个Excel文件对应于一个workbook(HSSFWorkbook)，
 * 一个workbook可以有多个sheet（HSSFSheet）组成，
 * 一个sheet是由多个row（HSSFRow）组成，
 * 一个row是由多个cell（HSSFCell）组成
 * https://www.cnblogs.com/GarfieldEr007/p/14540744.html
 * 总体思路如下：XLSX的文档读取类为XSSFWorkBook，其实现了WorkBook接口，可通过File类进行构造。然后通过Excel文档按照需求读取对应的工作表，
 * 再从工作表中根据所需行号等信息读取对应的行，最后根据列号定位到表中的单元格。该API可以从单元格中读取字符串、整形数、浮点数、日期和公式等数据
 */
public final class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * @param fileName
     * @param excelType
     * @return
     */
    public static boolean createEmptyExcel(String fileName, String excelType) {
        return true;
    }

    private static final String XLS = ".xls";
    private static final String XLSX = ".xlsx";

    /**
     * 根据文件后缀获取对应Workbook对象
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
     * @param filePath
     * @return
     */
    public static List<Map<String, String>> readExcel(String filePath) {
        Workbook workbook = null;
        List<Map<String, String>> resultList;
        try {
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            if (StringUtils.isEmpty(fileType)) fileType = "xlsx";
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
     * @param filePath
     * @param rowNum
     * @return
     */
    public static Map<String, String> readExcelRow(String filePath, int rowNum) {
        Workbook workbook = null;
        List<Map<String, String>> resultList;
        try {
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            if (StringUtils.isEmpty(fileType)) fileType = "xlsx";
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
     * @param workbook
     * @return
     */
    public static List<Map<String, String>> analysisExcel(Workbook workbook) {
        List<Map<String, String>> dataList = new ArrayList<>();
        int sheetCount = workbook.getNumberOfSheets();//或取一个Excel中sheet数量
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
            Row firstRow = sheet.getRow(firstRowCount);
            int cellCount = firstRow.getLastCellNum();//获取列数
            List<String> mapKey = new ArrayList<>();
            //获取表头信息，放在List中备用
            for (int i1 = 0; i1 < cellCount; i1++) {
                mapKey.add(firstRow.getCell(i1).toString());
            }
            //解析每一行数据，构成数据对象
            int rowStart = firstRowCount + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int j = rowStart; j < rowEnd; j++) {
                Row row = sheet.getRow(j);//获取对应的row对象
                if (row == null) {
                    continue;
                }
                //将每一行数据转化为一个Map对象
                Map<String, String> dataMap = convertRowToData(row, cellCount, mapKey);
                dataList.add(dataMap);
            }
        }
        return dataList;
    }

    /**
     * 将每一行数据转化为一个Map对象
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
        if (cel.getCellType() == CellType.FORMULA) {  //公式
            return cel.getCellFormula() + "";
        }
        return cel.toString();
    }

    public static void main(String[] args) throws IOException {
        //读取文件夹，批量解析Excel文件
        System.out.println("--------------------读取文件夹，批量解析Excel文件-----------------------");
        List<List<Map<String, String>>> returnList = readFolder("D:\\Temp");
        for (int i = 0; i < returnList.size(); i++) {
            List<Map<String, String>> maps = (List<Map<String, String>>) returnList.get(i);
            if (maps.isEmpty()) {
                continue;
            }
            for (int j = 0; j < maps.size(); j++) {
                System.out.println(maps.get(j).toString());
            }
            System.out.println("--------------------手打List切割线-----------------------");
        }

        //读取单个文件
        System.out.println("--------------------读取并解析单个文件-----------------------");
        List<Map<String, String>> maps = readExcel("D:\\Temp\\1.xlsx");
        for (int j = 0; j < maps.size(); j++) {
            System.out.println(maps.get(j).toString());
        }

        System.out.println("数据加载...");
        List<Map<String, Object>> mapArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("姓名", i);
            map.put("年龄", i);
            map.put("性别", i);
            mapArrayList.add(map);
        }
        System.out.println("数据加载完成...");

        String excel = writeExcel(mapArrayList, "1.xlsx", "Sheet-1");
        System.out.println(excel);
        File file = new File(excel);
        System.out.println(file);
        boolean b = FileUtils.openDirectory(file);
    }

    public static void writeExcelAndShow(List<Map<String, Object>> data, String title) throws IOException {
        String path = writeExcel(data, "tmp.xlsx", title);
        FileUtils.openFile(new File(path));
    }

    private static boolean checkIfExists(File file) {
        return file.exists();
    }

    /**
     * 将 List<Map<String,Object>> 类型的数据导出为 Excel
     * 默认 Excel 文件的输出路径为 项目根目录下
     * 文件名为 filename + 时间戳 + .xlsx
     * @param mapList  数据源(通常为数据库查询数据)
     * @param filename 文件名前缀, 实际文件名后会加上日期
     * @param title    表格首行标题
     * @return 文件输出路径
     */
    public static String writeExcel(List<Map<String, Object>> mapList, String filename, String title) throws IOException {
        //获取数据源的 key, 用于获取列数及设置标题
        Map<String, Object> map = mapList.get(0);
        Set<String> stringSet = map.keySet();
        ArrayList<String> headList = new ArrayList<>(stringSet);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(title); //创建一个Sheet页
        //为有数据的每列设置列宽
        for (int i = 0; i < headList.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        //设置单元格字体样式
        XSSFFont font = wb.createFont();
        font.setFontName("等线");
        font.setFontHeightInPoints((short) 16);
        //在sheet里创建第一行，并设置单元格内容为 title (标题)
        XSSFRow titleRow = sheet.createRow(0);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headList.size() - 1));
        // 创建单元格文字居中样式并设置标题单元格居中
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(cellStyle);
        //获得表格第二行
        XSSFRow row = sheet.createRow(1);
        //根据数据源信息给第二行每一列设置标题
        for (int i = 0; i < headList.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headList.get(i));
        }
        XSSFRow rows;
        XSSFCell cells;
        //循环拿到的数据给所有行每一列设置对应的值
        for (int i = 0; i < mapList.size(); i++) {
            //在这个sheet页里创建一行
            rows = sheet.createRow(i + 2);
            //给该行数据赋值
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
        writeWorkbook(wb, new File(StringUtils.uuid() + File.separator + filename));
        return "";
    }

    public static Workbook createWorkBook() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        return workbook;
    }

    /**
     * 创建字符串的Excel表
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
     * @param workbook
     * @param file
     */
    public static void writeWorkbook(Workbook workbook, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
            fos.flush();
        } catch (IOException e) {
            logger.error("failed to write workbook to {}, cause : {}", file.getAbsolutePath(), e.getMessage());
            throw e;
        }
    }
}
