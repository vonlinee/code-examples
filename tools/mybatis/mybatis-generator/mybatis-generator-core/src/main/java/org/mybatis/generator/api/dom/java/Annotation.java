package org.mybatis.generator.api.dom.java;

import java.util.List;
import java.util.Set;

/**
 * 注解类型
 */
public class Annotation extends JavaElement implements CompilationUnit {

    @Override
    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return null;
    }

    @Override
    public Set<String> getStaticImports() {
        return null;
    }

    @Override
    public FullyQualifiedJavaType getType() {
        return null;
    }

    @Override
    public void addImportedType(FullyQualifiedJavaType importedType) {

    }

    @Override
    public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {

    }

    @Override
    public void addStaticImport(String staticImport) {

    }

    @Override
    public void addStaticImports(Set<String> staticImports) {

    }

    @Override
    public void addFileCommentLine(String commentLine) {

    }

    @Override
    public List<String> getFileCommentLines() {
        return null;
    }

    @Override
    public <R> R accept(CompilationUnitVisitor<R> visitor) {
        return null;
    }
}
