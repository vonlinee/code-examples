package use;

import org.apache.velocity.Template;
import org.apache.velocity.runtime.parser.node.*;

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
            recursive(sn);
        } else {
            throw new RuntimeException(String.valueOf(data.getClass()));
        }
    }

    /**
     * 递归遍历AST树 深度优先
     *
     * @param parent AST点
     */
    private void recursive(Node parent) {
        int numOfChildren = parent.jjtGetNumChildren();
        if (numOfChildren <= 0) {
            return;
        }
        for (int i = 0; i < numOfChildren; i++) {
            Node node = parent.jjtGetChild(i);
            if (node instanceof ASTText) {
                // 普通文本节点
                ASTText astText = (ASTText) node;
                recursive(astText);
            } else if (node instanceof ASTReference) {
                // ASTReference的子节点为ASTIdentifier或ASTMethod
                ASTReference astReference = (ASTReference) node;
                Node node1 = astReference.jjtGetParent();
                System.out.println("父节点" + node1.getClass().getSimpleName() + "当前节点" + astReference.getRootString() + " " + astReference.literal());
                int num = astReference.jjtGetNumChildren();
                for (int j = 0; j < num; j++) {
                    Node childNode = astReference.jjtGetChild(j);
                    if (childNode instanceof ASTIdentifier) {
                        ASTIdentifier identifier = (ASTIdentifier) childNode;
                        System.out.println(identifier.getIdentifier());
                    } else if (childNode instanceof ASTMethod) {
                        ASTMethod method = (ASTMethod) childNode;

                    }
                }
            } else if (node instanceof ASTDirective) {
                ASTDirective astDirective = (ASTDirective) node;
                recursive(astDirective);
            } else if (node instanceof ASTIfStatement) {
                // #if
                ASTIfStatement astIfStatement = (ASTIfStatement) node;
                recursive(astIfStatement);
            } else if (node instanceof ASTElseIfStatement) {
                ASTElseIfStatement astElseIfStatement = (ASTElseIfStatement) node;
                recursive(astElseIfStatement);
            } else if (node instanceof ASTComment) {
                ASTComment astComment = (ASTComment) node;
                recursive(astComment);
            } else if (node instanceof ASTBlock) {
                ASTBlock astBlock = (ASTBlock) node;
                recursive(astBlock);
            } else if (node instanceof ASTDivNode) {
                ASTDivNode astDivNode = (ASTDivNode) node;
                recursive(astDivNode);
            } else if (node instanceof ASTExpression) {
                ASTExpression astExpression = (ASTExpression) node;
                recursive(astExpression);
            } else if (node instanceof ASTVariable) {
                ASTVariable astVariable = (ASTVariable) node;
                recursive(astVariable);
            } else if (node instanceof ASTMethod) {
                ASTMethod astMethod = (ASTMethod) node;
                recursive(astMethod);
            } else if (node instanceof ASTIdentifier) {
                ASTIdentifier astIdentifier = (ASTIdentifier) node;
                recursive(astIdentifier);
            }
        }
    }

    private void fill(ASTReference ref, Map<String, Integer> map) {

    }
}
