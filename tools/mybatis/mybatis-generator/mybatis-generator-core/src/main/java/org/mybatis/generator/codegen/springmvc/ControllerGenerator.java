package org.mybatis.generator.codegen.springmvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

/**
 * TODO
 * 生成Controller类
 */
public class ControllerGenerator extends AbstractJavaGenerator {

    public ControllerGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();

        String doName = fullyQualifiedTable.getDomainObjectName();

        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(controllerName(doName));
        //
        TopLevelClass controllerClass = new TopLevelClass(fullyQualifiedJavaType);
        controllerClass.setVisibility(JavaVisibility.PUBLIC);
        controllerClass.addAnnotation("@RestController");
        controllerClass.addAnnotation("@RequestMapping");
        // 导入的类型
        controllerClass.addStaticImports(controllerImportedTypes());

        // 控制器方法
        Method method = new Method("query");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation("@PostMapping(\"/request\")");

        controllerClass.addMethod(method);

        List<CompilationUnit> list = new ArrayList<>();
        list.add(controllerClass);
        return list;
    }

    private Set<String> controllerImportedTypes() {
    	Set<String> importedTypes = new HashSet<>();
    	importedTypes.add("org.springframework.web.bind.annotation.RestController");
    	importedTypes.add("org.springframework.web.bind.annotation.RequestMapping");
    	importedTypes.add("org.springframework.web.bind.annotation.PostMapping");
    	return importedTypes;
    }

    private String controllerName(String tableName) {
        return tableName + "Controller";
    }
}
