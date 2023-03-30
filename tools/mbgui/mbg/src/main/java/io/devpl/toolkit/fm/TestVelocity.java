package io.devpl.toolkit.fm;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @apiNote
 * @since 2022
 */
public class TestVelocity {
    public static void main(String[] args) {
        // 初始化模板引擎
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        // 获取模板文件
        Template t = ve.getTemplate("test.vm");
        // 设置变量
        VelocityContext ctx = new VelocityContext();
        ctx.put("packageName", "io.devpl.toolkit.fm");
//        ctx.put("accessModifier", "public");
        List<String> list = new ArrayList<>();
        list.add("java.io.StringWriter");
        list.add("java.io.StringWriter");
        ctx.put("importList", list);
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw, null);
        System.out.println(sw);

        parseTemplate(t);

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
