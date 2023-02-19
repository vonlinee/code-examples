package use;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.printer.configuration.DefaultConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.PrinterConfiguration;
import io.devpl.codegen.mbpg.util.FastJsonUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * https://houbb.github.io/2020/05/29/java-ast-06-comments
 *
 * @author wangliang
 * Created On 2022-12-29 10:11:33
 */
public class JavaParserUtils {

    private static final JavaParser JAVA_PARSER_INSTANCE;

    static {
        ParserConfiguration parserConfig = new ParserConfiguration();
        parserConfig.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_8); // JDK8
        parserConfig.setCharacterEncoding(StandardCharsets.UTF_8); // 源代码字符编码
        // 关闭注释分析 默认情况下启用注释分析，禁用将加快解析速度，但在处理单个源文件时速度提升不明显，如果要解析大量文件，建议禁用。
        parserConfig.setAttributeComments(true);
        // 设置为孤立注释
        parserConfig.setDoNotAssignCommentsPrecedingEmptyLines(true);
        JAVA_PARSER_INSTANCE = new JavaParser(parserConfig);
    }

    // 打印配置
    static PrinterConfiguration printerConfiguration;

    static {
        printerConfiguration = new DefaultPrinterConfiguration();
        printerConfiguration.addOption(new DefaultConfigurationOption(DefaultPrinterConfiguration.ConfigOption.PRINT_COMMENTS, true));
        printerConfiguration.addOption(new DefaultConfigurationOption(DefaultPrinterConfiguration.ConfigOption.PRINT_JAVADOC, true));
        printerConfiguration.addOption(new DefaultConfigurationOption(DefaultPrinterConfiguration.ConfigOption.COLUMN_ALIGN_FIRST_METHOD_CHAIN, true));
    }

    public static String toString(CompilationUnit compilationUnit) {
        return compilationUnit.toString(printerConfiguration);
    }

    public static Name newTypeName(String typeName) {
        final int i = typeName.lastIndexOf(".");
        if (i < 0) {
            return new Name(typeName);
        }
        return new Name(new Name(typeName.substring(0, i)), typeName.substring(i + 1));
    }

    public static void json2ObjectSchema(String jsonStr, String packageName, String className) {
        final Map<String, Object> map = FastJsonUtils.toMap(jsonStr);
        final Name annoJsonAlias = newTypeName("com.fasterxml.jackson.annotation.JsonAlias");
        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration(packageName);
        cu.addImport(new ImportDeclaration(annoJsonAlias, false, false));
        ClassOrInterfaceDeclaration book = cu.addClass(className);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            Type fieldType;
            final Class<?> type = value.getClass();
            // JSON序列化不会序列化为基本类型，一般都是包装类
            if (type == Integer.class) {
                fieldType = Integer.class;
            } else if (type == String.class) {
                fieldType = String.class;
            } else {
                fieldType = String.class;
            }
            ParseResult<ClassOrInterfaceType> result = JAVA_PARSER_INSTANCE.parseClassOrInterfaceType(fieldType.getTypeName());

            result.getResult().ifPresent(data -> {
                FieldDeclaration field = book.addField(data, key, Modifier.Keyword.PRIVATE);
                NormalAnnotationExpr annotationExpr = new NormalAnnotationExpr();
                annotationExpr.setName(annoJsonAlias.getIdentifier());

                // 注意添加的顺序：注释在注解的前面，否则不会打印注释
                final JavadocComment fieldComment = new JavadocComment(key);
                field.addOrphanComment(fieldComment);

                final NodeList<MemberValuePair> annoMemberMap = new NodeList<>();
                final MemberValuePair annoMember = new MemberValuePair();
                annoMember.setName("value");
                annoMember.setValue(new StringLiteralExpr(key));
                annoMemberMap.add(annoMember);
                annotationExpr.setPairs(annoMemberMap);

                field.addAnnotation(annotationExpr);
            });
        }
        System.out.println(toString(cu));
    }

    public static List<FieldsData> getFieldsDataList(String filePath) {
        try (FileInputStream in = new FileInputStream(filePath)) {
            ParseResult<CompilationUnit> cu = JAVA_PARSER_INSTANCE.parse(in);
            System.out.println(cu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        getFieldsDataList("D:\\Work\\Code\\campus-potrait-ws\\src\\main\\java\\com\\lancoo\\campusportrait\\domain\\vo\\TeacherDataViewVO.java");

        final ClassOrInterfaceDeclaration classOrInterfaceDeclaration = new ClassOrInterfaceDeclaration();
    }
}
