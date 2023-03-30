package use;

import org.apache.velocity.Template;
import org.apache.velocity.runtime.parser.node.*;

public class VelocityTemplateAnalyzer implements TemplateVariableAnalyzer<Template> {

    @Override
    public boolean support(Class<?> templateClass) {
        return templateClass == Template.class;
    }

    @Override
    public void analyze(Template template) {
        parseTemplate(template);
    }

    public static void parseTemplate(Template template) {
        Object data = template.getData();
        if (data instanceof SimpleNode) {
            SimpleNode sn = (SimpleNode) data;
            recursive(sn);
        } else {
            throw new RuntimeException(String.valueOf(data.getClass()));
        }
    }

    /**
     * 递归遍历AST树
     * 深度优先
     *
     * @param parent AST点
     */
    public static void recursive(Node parent) {
        int numOfChildren = parent.jjtGetNumChildren();
        if (numOfChildren > 0) {
            for (int i = 0; i < numOfChildren; i++) {
                Node node = parent.jjtGetChild(i);
                if (node instanceof ASTText) {
                    ASTText astText = (ASTText) node;
                    recursive(astText);
                } else if (node instanceof ASTReference) {
                    ASTReference astReference = (ASTReference) node;
                    System.out.println("RootString => " + astReference.getRootString());
                    recursive(astReference);
                } else if (node instanceof ASTDirective) {
                    ASTDirective astDirective = (ASTDirective) node;
                    System.out.println("指令" + astDirective.getDirectiveName());
                    recursive(astDirective);
                } else if (node instanceof ASTIfStatement) {
                    ASTIfStatement astIfStatement = (ASTIfStatement) node;
                    recursive(astIfStatement);
                } else if (node instanceof ASTComment) {
                    ASTComment astComment = (ASTComment) node;
                    recursive(astComment);
                } else if (node instanceof ASTBlock) {
                    ASTBlock astBlock = (ASTBlock) node;
                    recursive(astBlock);
                } else if (node instanceof ASTDivNode) {
                    ASTDivNode astDivNode = (ASTDivNode) node;
                    recursive(astDivNode);
                }
            }
        }
    }
}
