package io.devpl.codegen.meta;

import com.github.javaparser.ast.CompilationUnit;
import java.util.Map;

public interface CompilationUnitAnalyzer {

    Map<String, Object> analyse(CompilationUnit unit);
}
