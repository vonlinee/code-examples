package io.maker.extension.mybatis.mapper;

import io.maker.extension.poi.ExcelUtils;

import java.util.List;
import java.util.Map;

public class XmlMapperGenerator {

    public static void main(String[] args) {

        String filePath = "C:\\Users\\vonline\\Desktop\\code-samples\\tools\\trouble-maker\\mybatis-extensions\\src\\main\\resources\\excel\\单表生成模板.xlsx";
        List<Map<String, String>> dataList = ExcelUtils.readExcel(filePath);


        System.out.println(dataList);

    }
}
