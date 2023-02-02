package io.devpl.toolkit.fxui.bridge;

import org.mybatis.generator.api.CompositePlugin;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * 每个Context有一个PluginAggregator对象
 * 只会添加MBGPlugin这一个插件，MBGPlugin管理程序中用到的所有插件
 */
public class PluginManager extends CompositePlugin {

    private final Map<Class<?>, PluginConfiguration> plugins = new HashMap<>();

    @Override
    public void setContext(Context context) {

    }

    @Override
    public void setProperties(Properties properties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean validate(List<String> warnings) {
        throw new UnsupportedOperationException();
    }

    public void addIfAbsent(Class<?> pluginClass, PluginConfiguration pluginConfiguration) {
        if (plugins.computeIfAbsent(pluginClass, plugin -> pluginConfiguration) != null) {

        }
    }
}
