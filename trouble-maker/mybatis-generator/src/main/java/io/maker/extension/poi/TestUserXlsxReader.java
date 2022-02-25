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

//https://blog.csdn.net/vbirdbest/article/details/72870714
//
//        HSSF － 提供读写Microsoft Excel XLS格式档案的功能。
//        XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
//        HWPF － 提供读写Microsoft Word DOC97格式档案的功能。
//        XWPF － 提供读写Microsoft Word DOC2003格式档案的功能。
//        HSLF － 提供读写Microsoft PowerPoint格式档案的功能。
//        HDGF － 提供读Microsoft Visio格式档案的功能。
//        HPBF － 提供读Microsoft Publisher格式档案的功能。
//        HSMF － 提供读Microsoft Outlook格式档案的功能。
//
//        HSSF 是Horrible SpreadSheet Format的缩写，通过HSSF，
//        你可以用纯Java代码来读取、写入、修改Excel文件。
//        HSSF 为读取操作提供了两类API：usermodel和eventusermodel，即“用户模型”和“事件-用户模型”。
//
//
//        Excel文档分为XLS（针对Excel 97-2003）格式和XLSX（针对Excel 2007及以后版本）格式，不同格式所需的JAR包依赖是不一样的
//
//        既支持XLS格式，也支持XLSX格式
//<dependency>
//<groupId>org.apache.poi</groupId>
//<artifactId>poi-ooxml</artifactId>
//<version>3.11-beta1</version>
//</dependency>
//