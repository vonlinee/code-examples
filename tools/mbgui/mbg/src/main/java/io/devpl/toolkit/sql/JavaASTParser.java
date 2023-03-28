package io.devpl.toolkit.sql;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import io.devpl.toolkit.entity.DDLExtractor;
import io.devpl.toolkit.entity.JavaFieldInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装github java parser
 *
 * @see <a href="https://blog.csdn.net/crabstew/article/details/89547472">...</a>
 */
public class JavaASTParser {

    private final JavaParser parser;

    public JavaASTParser() {
        ParserConfiguration pc = new ParserConfiguration();
        pc.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_8);
        TypeSolver typeSolver = new CombinedTypeSolver();
        pc.setSymbolResolver(new JavaSymbolSolver(typeSolver));
        parser = new JavaParser(pc);
    }

    public CompilationUnit parseSingleFile(String absolutePath) {
        Path path = Path.of(absolutePath).normalize();
        CompilationUnit compilationUnit = null;
        try {
            ParseResult<CompilationUnit> result = parser.parse(path);
            if (result.isSuccessful()) {
                compilationUnit = result.getResult().orElse(new CompilationUnit());
            }
        } catch (IOException e) {
            throw new RuntimeException("解析失败:" + absolutePath, e);
        }
        return compilationUnit;
    }

    public static void main(String[] args) throws IOException {

        JavaASTParser gjp = new JavaASTParser();

        // 1. 转换的是完整的Java文件
        File base = new File(""); // 根项目的路径
        String relativePath = "/mbg/src/main/java/io/devpl/toolkit/entity/TemplateInfo.java";
        String absolutePath = base.getCanonicalPath() + File.separator + relativePath;
        Path path = Paths.get(absolutePath).normalize();

        CompilationUnit compUnit = gjp.parseSingleFile(path.toAbsolutePath().toString());

        final List<JavaFieldInfo> list = new ArrayList<>();

        DDLExtractor ddlExtractor = new DDLExtractor();
        ddlExtractor.visit(compUnit, null);
        System.out.println(ddlExtractor.getSql());
    }

}
