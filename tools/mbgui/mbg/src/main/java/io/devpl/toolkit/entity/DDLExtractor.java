package io.devpl.toolkit.entity;

import cn.hutool.core.util.StrUtil;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import io.devpl.toolkit.sql.TypeMap;

import java.util.Optional;

public class DDLExtractor extends GenericVisitorAdapter<Object, Object> {

    private final StringBuilder sql = new StringBuilder("CREATE TABLE ");

    TypeMap typeMap = new TypeMap();

    @Override
    public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
        sql.append(StrUtil.toUnderlineCase(n.getName().asString())).append(" ");
        return super.visit(n, arg);
    }

    @Override
    public Object visit(FieldDeclaration n, Object arg) {
        final NodeList<VariableDeclarator> variables = n.getVariables();
        int count = variables.size();
        if (count == 1) {
            final Optional<VariableDeclarator> firstVar = variables.getFirst();
            if (firstVar.isPresent()) {
                final VariableDeclarator vd = firstVar.get();
                final Type type = vd.getType();

                System.out.println(vd.getDataKeys());

// System.out.println(type.getElementType().getClass());

            }
        }
        return super.visit(n, arg);
    }
}
