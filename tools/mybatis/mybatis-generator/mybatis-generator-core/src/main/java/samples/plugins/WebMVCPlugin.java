package samples.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * 生成web mvc的相关文件
 */
public class WebMVCPlugin extends PluginAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebMVCPlugin.class);

    public WebMVCPlugin() {
        super();
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        log.info("WebMVCPlugin initialized {}", introspectedTable.getTableConfiguration().getTableName());
    }

    @Override
    public boolean validate(List<String> warnings) {
        // this plugin is always valid
        return true;
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        // 先调
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        // 多个插件顺序执行
        return super.contextGenerateAdditionalJavaFiles();
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        // 添加生成Controller和Service
        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        return super.contextGenerateAdditionalXmlFiles(introspectedTable);
    }
}
