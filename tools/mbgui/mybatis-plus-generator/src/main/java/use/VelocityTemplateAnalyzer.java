package use;

import org.apache.velocity.Template;
import org.apache.velocity.runtime.parser.node.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://developer.aliyun.com/article/64918">...</a>
 */
public class VelocityTemplateAnalyzer implements TemplateVariableAnalyzer<Template> {

    @Override
    public boolean support(Class<?> templateClass) {
        return templateClass == Template.class;
    }

    @Override
    public void analyze(Template template) {
        parseTemplate(template);
    }

    private void parseTemplate(Template template) {
        Object data = template.getData();
        if (data instanceof SimpleNode) {
            SimpleNode sn = (SimpleNode) data;
            Map<String, Object> map = new HashMap<>();
            recursive(sn, map);
            System.out.println(map);
        } else {
            throw new RuntimeException(String.valueOf(data.getClass()));
        }
    }

    /**
     * 递归遍历AST树 深度优先
     *
     * @param parent AST点
     */
    private void recursive(Node parent, Map<String, Object> map) {
        int numOfChildren = parent.jjtGetNumChildren();
        if (numOfChildren <= 0) {
            return;
        }
        for (int i = 0; i < numOfChildren; i++) {
            Node node = parent.jjtGetChild(i);
            if (node instanceof ASTText) {
                // 普通文本节点
                ASTText astText = (ASTText) node;
                recursive(astText, map);
            } else if (node instanceof ASTReference) {
                // ASTReference的子节点为ASTIdentifier或ASTMethod
                ASTReference astReference = (ASTReference) node;
                checkVariableType(astReference, map);
            } else if (node instanceof ASTDirective) {
                //
                ASTDirective astDirective = (ASTDirective) node;
                recursive(astDirective, map);
            } else if (node instanceof ASTIfStatement) {
                // #if
                ASTIfStatement astIfStatement = (ASTIfStatement) node;
                recursive(astIfStatement, map);
            } else if (node instanceof ASTElseIfStatement) {
                ASTElseIfStatement astElseIfStatement = (ASTElseIfStatement) node;
                recursive(astElseIfStatement, map);
            } else if (node instanceof ASTComment) {
                ASTComment astComment = (ASTComment) node;
                recursive(astComment, map);
            } else if (node instanceof ASTBlock) {
                ASTBlock astBlock = (ASTBlock) node;
                recursive(astBlock, map);
            } else if (node instanceof ASTDivNode) {
                ASTDivNode astDivNode = (ASTDivNode) node;
                recursive(astDivNode, map);
            } else if (node instanceof ASTExpression) {
                ASTExpression astExpression = (ASTExpression) node;
                recursive(astExpression, map);
            } else if (node instanceof ASTVariable) {
                ASTVariable astVariable = (ASTVariable) node;
                recursive(astVariable, map);
            } else if (node instanceof ASTMethod) {
                ASTMethod astMethod = (ASTMethod) node;
                recursive(astMethod, map);
            } else if (node instanceof ASTIdentifier) {
                ASTIdentifier astIdentifier = (ASTIdentifier) node;
                recursive(astIdentifier, map);
            }
        }
    }

    private void checkVariableType(ASTReference ref, Map<String, Object> map) {
        Node parent = ref.jjtGetParent();
        if (parent instanceof ASTprocess) {
            return;
        }
        if (parent instanceof ASTDirective) {
            ASTDirective directive = (ASTDirective) parent;
            System.out.println(directive.getDirectiveName());
        } else if (parent instanceof ASTIfStatement) {
            //
        }
    }
}
