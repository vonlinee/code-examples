package io.devpl.toolkit.entity;

import com.github.javaparser.ast.CompilationUnit;

@FunctionalInterface
public interface CompilationUnitTravelStrategy<R> {

    /**
     * 遍历AST,得到想要的结果
     *
     * @param compilationUnit AST
     * @return 结果
     */
    R travel(CompilationUnit compilationUnit);
}
