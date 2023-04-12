package io.devpl.codegen.meta;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ASTFieldParser implements CompilationUnitVisitor<List<FieldMetaData>> {

    @Override
    public List<FieldMetaData> visit(CompilationUnit cu) {
        NodeList<TypeDeclaration<?>> types = cu.getTypes();
        for (TypeDeclaration<?> type : types) {
            List<FieldDeclaration> fields = type.getFields();
            for (FieldDeclaration field : fields) {

                if (field.isStatic()) {
                    continue; // 忽略静态变量
                }

                final NodeList<VariableDeclarator> variables = field.getVariables();

                for (VariableDeclarator variable : variables) {
                    final SimpleName name = variable.getName();
                    String fieldName = name.getIdentifier();

                    final Type type1 = variable.getType();
                    System.out.println(type1);
                }
            }
        }
        return new ArrayList<>();
    }
}
