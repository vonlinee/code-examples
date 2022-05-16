package samples;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;

import java.util.ArrayList;
import java.util.Date;
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
        return context;
    }

    private PluginConfiguration newPluginConfiguration(String typeName, Properties props) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(typeName);
        pluginConfiguration.getProperties().entrySet().addAll(props.entrySet());
        return pluginConfiguration;
    }
}
