package io.devpl.toolkit.fxui.plugins;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * MyBatis-Plus支持插件
 */
public class MyBatisPlusPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
