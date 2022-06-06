package org.mybatis.generator.codegen.springmvc;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

/**
 * 生成业务服务类
 */
public class BusinessServiceGenerator extends AbstractJavaGenerator {

    public BusinessServiceGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        return new ArrayList<>();
    }
}
