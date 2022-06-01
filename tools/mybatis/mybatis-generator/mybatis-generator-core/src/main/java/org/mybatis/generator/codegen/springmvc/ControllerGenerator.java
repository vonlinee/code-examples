package org.mybatis.generator.codegen.springmvc;

import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

/**
 * TODO
 * 生成Controller类
 */
public class ControllerGenerator extends AbstractJavaGenerator {

	protected ControllerGenerator(String project) {
		super(project);
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();


		String doName = fullyQualifiedTable.getDomainObjectName();

		FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(controllerName(doName));
		//
		TopLevelClass controllerClass = new TopLevelClass(fullyQualifiedJavaType);


		return null;
	}

	private String controllerName(String tableName) {
		return tableName + "Controller";
	}
}
