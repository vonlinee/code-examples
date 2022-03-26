package org.springboot.sample.config.viewresolver.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

/**
 * Excel视图输出
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年2月27日
 */
public class XlsView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        Sheet sheet = workbook.createSheet("sheet1");
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        // style.setAlignment(CellStyle.ALIGN_CENTER);
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int colCount = 0;
        // 创建头部
        row = sheet.createRow(rowCount++);
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("ID");
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("NAME");
        // 创建数据
        row = sheet.createRow(rowCount++);
        colCount = 0;
        // 这里为了测试，实际应用中，我们从model中读取值
        row.createCell(colCount++).setCellValue("100");
        row.createCell(colCount++).setCellValue("xiaoshan");
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }
}