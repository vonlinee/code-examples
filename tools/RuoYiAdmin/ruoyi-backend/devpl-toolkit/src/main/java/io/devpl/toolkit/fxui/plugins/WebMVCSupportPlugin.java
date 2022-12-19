package io.devpl.toolkit.fxui.plugins;

import io.devpl.toolkit.fxui.utils.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;

import java.util.ArrayList;
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
public class WebMVCSupportPlugin extends PluginAdapter {

    public WebMVCSupportPlugin() {

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

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        List<GeneratedJavaFile> mvcFiles = new ArrayList<>(prepareServiceFiles());
        mvcFiles.addAll(prepareControllerFiles());
        return mvcFiles;
    }

    private List<GeneratedJavaFile> prepareControllerFiles() {
        List<GeneratedJavaFile> controllerFiles = new ArrayList<>();
        final List<IntrospectedTable> introspectedTables = context.getIntrospectedTables();
        for (IntrospectedTable introspectedTable : introspectedTables) {

        }
        return controllerFiles;
    }

    private List<GeneratedJavaFile> prepareServiceFiles() {
        List<GeneratedJavaFile> serviceFiles = new ArrayList<>();
        final List<IntrospectedTable> introspectedTables = context.getIntrospectedTables();
        for (IntrospectedTable introspectedTable : introspectedTables) {
            TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();

            // final Interface serviceInterface = new Interface();
        }
        return serviceFiles;
    }
}
