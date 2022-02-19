package io.maker.extension.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.Date;

/**
 * 一个Excel文件对应于一个workbook(HSSFWorkbook)，
 * 一个workbook可以有多个sheet（HSSFSheet）组成，
 * 一个sheet是由多个row（HSSFRow）组成，
 * 一个row是由多个cell（HSSFCell）组成
 */
public class ExcelUtils {

//    总体思路如下：XLSX的文档读取类为XSSFWorkBook，其实现了WorkBook接口，可通过File类进行构造。然后通过Excel文档按照需求读取对应的工作表，再从工作表中根据所需行号等信息读取对应的行，最后根据列号定位到表中的单元格。该API可以从单元格中读取字符串、整形数、浮点数、日期和公式等数据

    public static boolean createNewFile() {
        return true;
    }

    public static void createExcel() throws IOException {
        // 获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/template.xls";
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("订单号");
        row.createCell(2).setCellValue("下单时间");
        row.createCell(3).setCellValue("个数");
        row.createCell(4).setCellValue("单价");
        row.createCell(5).setCellValue("订单金额");
        row.setHeightInPoints(30); // 设置行的高度
        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("NO00001");
        // 日期格式化
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        HSSFCreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        sheet.setColumnWidth(2, 20 * 256); // 设置列的宽度
        HSSFCell cell2 = row1.createCell(2);
        cell2.setCellStyle(cellStyle2);
        cell2.setCellValue(new Date());
        row1.createCell(3).setCellValue(2);
        // 保留两位小数
        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        HSSFCell cell4 = row1.createCell(4);
        cell4.setCellStyle(cellStyle3);
        cell4.setCellValue(29.5);
        // 货币格式化
        HSSFCellStyle cellStyle4 = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("华文行楷");
        font.setFontHeightInPoints((short) 15);
//        font.setColor(HSSFColor.RED.index);
        cellStyle4.setFont(font);
        HSSFCell cell5 = row1.createCell(5);
        cell5.setCellFormula("D2*E2");  // 设置计算公式
        // 获取计算公式的值
        HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(workbook);
        cell5 = e.evaluateInCell(cell5);
        System.out.println(cell5.getNumericCellValue());
        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();
    }

    public static void readExcel() throws IOException {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/template.xls";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheet("Sheet1");
        int lastRowIndex = sheet.getLastRowNum();
        System.out.println(lastRowIndex);
        for (int i = 0; i <= lastRowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                String cellValue = row.getCell(j).getStringCellValue();
                System.out.println(cellValue);
            }
        }
        bufferedInputStream.close();
    }

}
