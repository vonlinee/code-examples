package org.mybatis.generator.codegen.springmvc;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.*;
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

        // 控制器方法
        Method method = new Method("query");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation("@PostMapping(\"/request\")");

        controllerClass.addMethod(method);

        List<CompilationUnit> list = new ArrayList<>();
        list.add(controllerClass);
        return list;
    }

    private String controllerName(String tableName) {
        return tableName + "Controller";
    }
}
