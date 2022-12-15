package use;

import org.mybatis.generator.DefaultProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyBatisGenerator {

    public static void main(String[] args) throws Exception {
        String filename = "mbg-config.xml";
        // D:\Develop\Workspace\project\devpl-code-generator\src\main\resources\mbg-config.xml
        String configLocation = new File("").getAbsolutePath() + "/src/main/resources/" + filename;
        System.out.println(configLocation);
        File file = new File(configLocation);
        List<String> warnings = new ArrayList<>();
        // 解析XML配置
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(file);
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        org.mybatis.generator.api.MyBatisGenerator generator = new org.mybatis.generator.api.MyBatisGenerator(config, shellCallback, warnings);
        DefaultProgressCallback progressCallback = new DefaultProgressCallback();
        generator.generate(progressCallback, null, null, true);
    }
}
