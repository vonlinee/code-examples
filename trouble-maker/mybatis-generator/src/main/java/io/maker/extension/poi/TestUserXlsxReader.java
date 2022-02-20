package io.maker.extension.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUserXlsxReader {

    private static final ExcelWriter writer = new ExcelWriter();

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelTable excelTable = new ExcelTable("xlsx", "模特");
        for (int i = 0; i < 10; i++) {
            ExcelColumn<String> column = new ExcelColumn<>("列" + i, String.class);
            for (int j = 0; j < 4; j++) {
                column.add("值 " + i + "" + j);
            }
            excelTable.addColumn(column);
        }

        File file = new File("D:/Temp/1.xlsx");

        boolean write = writer.write(file, excelTable);
        if (write) {
            System.out.println("写入成功");
        }

        List<ExcelTable> excelTables = new ExcelReader().readXlsx(file);

        System.out.println(excelTables);
    }
}
