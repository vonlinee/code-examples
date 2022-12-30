package io.devpl.spring.extension.poi;

import io.devpl.sdk.CaseFormat;
import io.devpl.sdk.collection.Lists;
import io.devpl.sdk.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws IOException {

        // test1();
        test2();
    }

    public static void test2() {
        final List<Map<String, String>> rows = ExcelUtils.readExcel("D:/Temp/1.xlsx");
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE  (\n");
        for (int i = 1; i < rows.size(); i++) {
            final Map<String, String> row = rows.get(i);

            String code = row.get("code");
            String type = row.get("type");
            String description = row.get("description").trim();

            code = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE).convert(code);
            assert code != null;
            final int index = code.indexOf("_");
            code = code.substring(index + 1);

            if (type.startsWith("n")) {
                type = type.substring(1);
            }

            type = type.trim();

            sb.append("\t").append(code).append(" ").append(type).append(" COMMENT '").append(description).append("',\n");
        }
        sb.append("\tPRIMARY KEY (`school_code`) USING BTREE\n");
        sb.append(") ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 ROW_FORMAT = DYNAMIC COMMENT = '';");
        System.out.println(sb);
    }

    public static void test1() throws IOException {
        final List<String> strings = FileUtils.readLines(new File("D:/1.txt"));

        String names =
                "弹性云服务器\n" +
                        "云硬盘\n" +
                        "云数据库\n" +
                        "弹性公网\n" +
                        "弹性负载均衡\n" +
                        "分布式缓存服务\n" +
                        "分布式消息服务\n" +
                        "对象存储服务\n" +
                        "短信服务\n" +
                        "CDN服务\n" +
                        "视频直播\n" +
                        "实时音视频\n" +
                        "即时通信IM\n" +
                        "消息队列MQ\n" +
                        "SSL证书服务\n" +
                        "域名服务";

        final String[] split1 = names.split("\n");

        int i = -1;
        for (String string : strings) {
            i++;
            final String[] split = StringUtils.split(string, "、");
            Map<String, Object> data = new LinkedHashMap<>();
            for (String s : split) {
                data.put(s, "");
            }
            ExcelUtils.writeExcel(Lists.arrayOf(data), i + split1[i] + ".xlsx", split1[i]);
        }
    }
}
