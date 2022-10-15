package io.devpl.codegen.core.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 生成Controller和Service代码
 * @since created on 2022年8月5日
 */
public class FullMVCSupportPlugin extends PluginAdapter {

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
		List<IntrospectedTable> tables = context.getIntrospectedTables();
		List<GeneratedJavaFile> mvcFiles = new ArrayList<>();

		return mvcFiles;
	}
}
