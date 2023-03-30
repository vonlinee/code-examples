package use;

import org.apache.velocity.Template;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

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

    public static void recursive(Node parent) {
        int numOfChildren = parent.jjtGetNumChildren();
        if (numOfChildren > 0) {
            for (int i = 0; i < numOfChildren; i++) {
                Node node = parent.jjtGetChild(i);
                System.out.println(node.getClass());
            }
        }
    }
}
