package samples;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.PluginConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public static Configuration newConfiguration() {
        Configuration config = new Configuration();
        return config;
    }

    private static List<PluginConfiguration> pluginConfigurations() {
        PluginConfiguration pc = new PluginConfiguration();
        pc.setConfigurationType(""); // 插件类的全限定类名
        pc.addProperty("order", "1");
        pc.addProperty("", "");
        pc.addProperty("", "");
        return new ArrayList<>();
    }
}
