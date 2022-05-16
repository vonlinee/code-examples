package samples;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Utils {

    public static Configuration newConfiguration() {
        Configuration config = new Configuration();
        return config;
    }

    public Context newContext() {
        Context context = new Context(ModelType.CONDITIONAL);
        context.setId("");
        context.getCommentGenerator();
        Context context = new Context(ModelType.CONDITIONAL);
        // context.addPluginConfiguration();
        return context;
    }

    private PluginConfiguration newPluginConfiguration(String typeName, Properties props) {
        return pluginConfiguration;
    }


    private static List<PluginConfiguration> newPluginConfigurations() {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(typeName);
        pluginConfiguration.getProperties().entrySet().addAll(props.entrySet());
        PluginConfiguration pc = new PluginConfiguration();
        pc.setConfigurationType(""); // 插件类的全限定类名
        pc.addProperty("order", "1");
        pc.addProperty("", "");
        pc.addProperty("", "");
        return new ArrayList<>();
    }
}
