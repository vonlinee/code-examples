package org.mybatis.generator;

import org.apache.commons.io.FileUtils;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLGenerator {

    private static final String configFile = "/scripts/generatorConfig-local.xml";

    public static void main(String[] args) throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(MySQLGenerator.class.getResourceAsStream(configFile));
        // 进度回调
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, false);
        List<GeneratedXmlFile> generatedXmlFiles = myBatisGenerator.getGeneratedXmlFiles();

        for (int i = 0; i < generatedXmlFiles.size(); i++) {
            GeneratedXmlFile generatedXmlFile = generatedXmlFiles.get(i);
            ByteArrayInputStream is = new ByteArrayInputStream(generatedXmlFile.getFormattedContent().getBytes());
            try {
                FileUtils.copyInputStreamToFile(is, new File("/1.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
