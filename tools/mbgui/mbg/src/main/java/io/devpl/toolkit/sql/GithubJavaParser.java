package io.devpl.toolkit.sql;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import io.devpl.toolkit.entity.DDLExtractor;
import io.devpl.toolkit.entity.JavaFieldInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GithubJavaParser {

    public static CompilationUnit parseSingleFile(String absolutePath) {
        Path path = Path.of(absolutePath).normalize();
        CompilationUnit compilationUnit = null;
        try {
            ParseResult<CompilationUnit> result = new JavaParser().parse(path);
            if (result.isSuccessful()) {
                compilationUnit = result.getResult().orElse(new CompilationUnit());
            }
        } catch (IOException e) {
            throw new RuntimeException("解析失败:" + absolutePath);
        }
        return compilationUnit;
    }

    public static void main(String[] args) throws IOException {
        // 1. 转换的是完整的Java文件
        File base = new File(""); // 根项目的路径
        String relativePath = "/mbg/src/main/java/io/devpl/toolkit/entity/JdbcConnInfo.java";
        String absolutePath = base.getCanonicalPath() + File.separator + relativePath;
        Path path = Paths.get(absolutePath).normalize();

        CompilationUnit compUnit = parseSingleFile(path.toAbsolutePath().toString());

        final ArrayList<JavaFieldInfo> list = new ArrayList<>();
        final Object value = new DDLExtractor().visit(compUnit, list);
    }

}
