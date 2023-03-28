package io.devpl.toolkit.entity;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.devpl.toolkit.sql.SqlType;
import io.devpl.toolkit.sql.TypeMap;
import io.devpl.toolkit.utils.ClassUtils;
import io.devpl.toolkit.utils.FileUtils;

import java.util.*;
import java.util.function.Function;

/**
 * 解析Java实体类成表的DDL
 * 1.兼容JPA
 * 2.兼容MyBatis-Plus
 * 不考虑索引
 */
public class DDLExtractor extends VoidVisitorAdapter<Void> {

    private final StringBuilder sql = new StringBuilder("CREATE TABLE ");

    TypeMap typeMap = new TypeMap();

    // 导入的类型
    private Map<String, String> importItems = new HashMap<>();

    public DDLExtractor() {
        // 8种基本类型
        importItems.put("Integer", Integer.class.getName());
        importItems.put("Byte", Byte.class.getName());
        importItems.put("Float", Float.class.getName());
        importItems.put("Double", Double.class.getName());
        importItems.put("Short", Short.class.getName());
        importItems.put("Boolean", Boolean.class.getName());
        importItems.put("Character", Character.class.getName());
        importItems.put("Long", Long.class.getName());
        // 常用的数据类型
        importItems.put("String", String.class.getName());
        importItems.put("CharSequence", CharSequence.class.getName());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        sql.append(StrUtil.toUnderlineCase(n.getName().asString())).append(" (\n");
        super.visit(n, arg);
    }

    @Override
    public void visit(CompilationUnit n, Void arg) {
        buildImportIndex(n);
        super.visit(n, arg);
    }

    private void buildImportIndex(CompilationUnit cu) {
        NodeList<ImportDeclaration> imports = cu.getImports();
        for (ImportDeclaration importDeclaration : imports) {
            String name = importDeclaration.getName().asString();
            importItems.put(FileUtils.getExtension(name), name);
        }
    }

    /**
     * 只提取有效的注释信息
     *
     * @param rawContent 注释文本
     * @return
     * @see JavadocComment#getContent()
     */
    private String beautifyComment(String rawContent) {
        String str = rawContent.replaceAll(" ", "").replaceAll("\n", "");
        char[] chars = str.toCharArray();
        int index = 0;
        for (; index < chars.length; index++) {
            if ('@' == chars[index]) {
                break;
            }
        }
        return str.substring(0, index);
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
        if (isStatic(n)) {
            super.visit(n, arg);
            return;
        }

        // 获取字段注释信息
        String comment = getComment(n);

        comment = beautifyComment(comment);

        final NodeList<VariableDeclarator> variables = n.getVariables();
        int count = variables.size();
        // 不建议一个字段有多个变量
        if (count == 1) {
            final Optional<VariableDeclarator> firstVar = variables.getFirst();
            if (firstVar.isPresent()) {
                final VariableDeclarator vd = firstVar.get();

                SimpleName vdName = vd.getName();

                // 得到字段类型
                Class<?> typeClass = getTypeClass(vd.getType());

                if (typeClass == List.class || typeClass == Set.class || typeClass == Map.class) { // 跳过
                    super.visit(n, arg);
                    return;
                }

                SqlType sqlType = typeMap.get(typeClass);

                String columnName = NamingCase.toUnderlineCase(vdName.getIdentifier());

                sql.append("`").append(columnName).append("`").append(" ").append(sqlType).append(" COMMENT '").append(comment).append("',\n");
            }
        }
        super.visit(n, arg);
    }

    /**
     * 是否是静态字段
     *
     * @param fd
     * @return
     */
    private static boolean isStatic(FieldDeclaration fd) {
        NodeList<Modifier> modifiers = fd.getModifiers();
        for (Modifier modifier : modifiers) {
            if (modifier.getKeyword() == Modifier.Keyword.STATIC) {
                return true;
            }
        }
        return false;
    }

    private String getComment(FieldDeclaration fd) {
        String content = null;
        Optional<JavadocComment> javadocCommentOptional = fd.getJavadocComment();
        if (javadocCommentOptional.isPresent()) {
            JavadocComment javadocComment = javadocCommentOptional.get();
            Optional<Comment> commentOptional = javadocComment.getComment();
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                content = comment.getContent();
            }
            if (content == null) {
                content = javadocComment.getContent().trim();
            }
        }
        if (content == null) {
            return "注释信息";
        }
        return content.replace("*", "").trim();
    }

    /**
     * 仅支持8种基本类型或者几种常见的数据类型，比如Date，LocalDateTime
     *
     * @param type
     * @return
     */
    private Class<?> getTypeClass(Type type) {
        Class<?> result = null;
        if (type.isPrimitiveType()) {
            result = type.toPrimitiveType().map((Function<PrimitiveType, Class<?>>) primitiveType -> ClassUtils.forName(primitiveType.asString())).orElse(null);
            if (result == null) {
                result = int.class; // 默认类型
            }
        } else if (type.isReferenceType()) {
            result = type.toReferenceType().map((Function<ReferenceType, Class<?>>) referenceType -> {
                Node node = referenceType.getChildNodes().get(0);
                Class<?> refTypeClass = null;
                if (node instanceof SimpleName) {
                    String identifier = ((SimpleName) node).getIdentifier();

                    if (importItems.containsKey(identifier)) {
                        String fieldType = importItems.get(identifier);
                        refTypeClass = ClassUtils.forName(fieldType);
                    } else {
                        // 可能是写的类型全名称或者有语法错误
                        if (identifier.contains(".")) {
                            // TODO 特殊处理
                        } else if (identifier.contains("<") || identifier.contains(">")) {
                            // 泛型类型
                        }
                    }
                }
                return refTypeClass;
            }).orElse(String.class); // 默认类型
        }
        return result;
    }

    public String getSql() {
        String s = sql.toString();
        s += ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='xxx表';";
        return s;
    }
}
