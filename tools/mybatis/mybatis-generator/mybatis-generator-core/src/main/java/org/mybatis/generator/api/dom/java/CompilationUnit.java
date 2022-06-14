package org.mybatis.generator.api.dom.java;

import java.util.List;
import java.util.Set;

/**
 * This interface describes methods common to all Java compilation units (Java
 * classes, interfaces, and enums).
 * 编译单元（类/接口/枚举）  此接口描述所有Java编译单元通用的方法
 * @author Jeff Butler
 */
public interface CompilationUnit {

    /**
     * 导入的类型
     * @return
     */
    Set<FullyQualifiedJavaType> getImportedTypes();

    /**
     * 静态导入
     * @return
     */
    Set<String> getStaticImports();

    /**
     * Java类型
     * @return
     */
    FullyQualifiedJavaType getType();

    void addImportedType(FullyQualifiedJavaType importedType);

    void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes);

    /**
     * 新增，直接添加字面量
     * @param importedItem
     */
    // void addImported(String importedItem);

    /**
     * 新增，直接添加字面量
     * @param importedCollection
     */
    // void addImported(Set<String> importedCollection);

    void addStaticImport(String staticImport);

    void addStaticImports(Set<String> staticImports);

    /**
     * Comments will be written at the top of the file as is, we do not append any start or end comment characters.
     * <p>Note that in the Eclipse plugin, file comments will not be merged.
     *
     * @param commentLine the comment line
     */
    void addFileCommentLine(String commentLine);

    List<String> getFileCommentLines();

    /**
     * 访问者模式遍历
     * @param visitor
     * @param <R>
     * @return
     */
    <R> R accept(CompilationUnitVisitor<R> visitor);
}
