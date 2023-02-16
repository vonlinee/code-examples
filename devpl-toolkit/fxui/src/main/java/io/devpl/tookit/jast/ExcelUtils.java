package io.devpl.tookit.jast;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtils {

    // 自适应宽度(中文支持)
    public static void setSizeColumn(XSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            // 获取列宽
            int columnWidth = sheet.getColumnWidth(columnNum);
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                // 当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    XSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int count = 0;// 汉字数量
                        String regEx = "[\\u4e00-\\u9fa5]";
                        Pattern p = Pattern.compile(regEx);
                        Matcher m = p.matcher(currentCell.getStringCellValue());
                        int len = m.groupCount();
                        // 获取汉字个数
                        while (m.find()) {
                            for (int i = 0; i <= len; i++) {
                                count = count + 1;
                            }
                        }
                        // 因为程序中将汉字编译成一个字符，因此我们在该列字符长度的基础上加上汉字个数计算列宽
                        int length = (currentCell.getStringCellValue()
                                .length() + count) * 256;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            // 设置列宽
            sheet.setColumnWidth(columnNum, columnWidth);
        }
    }
}
