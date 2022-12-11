package io.devpl.codegen.fxui.plugins;

import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;

import java.util.List;
import java.util.Properties;

/**
 * 创建MVC三层的代码
 * 包含
 * 1.控制器
 * 2.Service
 * 3.和DAO层Mapper
 * @since created on 2022年8月5日
 */
public class FullMVCSupportPlugin extends PluginAdapter {

    public FullMVCSupportPlugin() {

    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

}
