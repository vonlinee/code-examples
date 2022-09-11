package org.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.4.1版本，最新版
 */
public class MySQLGenerator {

    public static void main(String[] args) throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {

        String pathname = "C:\\Users\\vonline\\Desktop\\code-samples\\devpl-sdk\\devpl-codegen\\src\\main\\resources\\generatorConfig.xml";


        File file = new File(pathname);

        FileInputStream fis = new FileInputStream(file);


        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(fis);
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, true);
    }
}
