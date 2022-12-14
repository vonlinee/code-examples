package org.mybatis.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MySQLGenerator {

    public static void main(String[] args) throws Exception {
        String filename = "mbg-config.xml";
        // D:\Develop\Workspace\project\devpl-code-generator\src\main\resources\mbg-config.xml
        String configLocation = new File("").getAbsolutePath() + "/src/main/resources/" + filename;
        System.out.println(configLocation);
        File file = new File(configLocation);
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(file);
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator generator = new MyBatisGenerator(config, shellCallback, warnings);
        DefaultProgressCallback progressCallback = new DefaultProgressCallback();
        generator.generate(progressCallback, null, null, true);
    }
}
