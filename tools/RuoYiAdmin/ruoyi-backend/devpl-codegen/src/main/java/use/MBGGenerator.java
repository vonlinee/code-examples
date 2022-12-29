package use;

import io.devpl.sdk.util.ResourceUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.plugins.SerializablePlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MBGGenerator {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        File file = ResourceUtils.getFile("classpath:mbg-config.xml");
        // 解析XML配置
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(file);
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator generator = new MyBatisGenerator(config, shellCallback, warnings);
        DefaultProgressCallback progressCallback = new DefaultProgressCallback();

        for (Context context : config.getContexts()) {
            context.addPluginConfiguration(SerializablePlugin.class);
        }

        generator.generate(progressCallback, null, null, true);
    }
}
