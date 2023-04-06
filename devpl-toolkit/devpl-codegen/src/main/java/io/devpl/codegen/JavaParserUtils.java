package io.devpl.codegen;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class JavaParserUtils {

    private static final JavaParser javaParser = new JavaParser();

    public static CompilationUnit parse(Path path) throws IOException {
        ParseResult<CompilationUnit> result = javaParser.parse(path);
        if (!result.isSuccessful()) {
            throw new RuntimeException("failed to parse file");
        }
        final Optional<CompilationUnit> resultOption = result.getResult();
        if (resultOption.isEmpty()) {
            throw new RuntimeException("failed to parse file");
        }
        return resultOption.get();
    }

    public static CompilationUnit parse(String pathname) throws IOException {
        return parse(Path.of(pathname));
    }
}
