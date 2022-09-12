package io.devpl.codegen.mbg.plugins;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;

/**
 * 
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

	// @Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		// return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
		return true;
	}
}
