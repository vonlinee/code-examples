package io.devpl.tools.jast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public class JavaParserUtils {

    private static final JavaParser javaParser = new JavaParser();

    public static CompilationUnit parse(String pathname) throws IOException {
        ParseResult<CompilationUnit> result = javaParser.parse(Paths.get(pathname));
        if (!result.isSuccessful()) {
            throw new RuntimeException("failed to parse file");
        }
        final Optional<CompilationUnit> resultOption = result.getResult();
        if (resultOption.isEmpty()) {
            throw new RuntimeException("failed to parse file");
        }
        return resultOption.get();
    }

    public static void main(String[] args) throws IOException {
        String absolutePath = "D:/Temp/Model.java";
        final CompilationUnit root = parse(absolutePath);

        System.out.println(root.getAllComments());

        System.out.println(root.getImports());
    }
}
