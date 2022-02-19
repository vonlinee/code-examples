package io.maker.extension;

import io.maker.extension.poi.ExcelWriter;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        ExcelWriter excelWriter = new ExcelWriter();

        excelWriter.write(new File("D:/1.xlsx"), null);
    }
}
