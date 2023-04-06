package use;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.util.Properties;

public class VelocityTest {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");//全局编码,如果以下编码不设置它就生效
        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");//输入流的编码，其实是打酱油!非模板文件编码

        props.setProperty(VelocityEngine.RESOURCE_LOADER,"file");//模板文件加载方式
        VelocityEngine ve = new VelocityEngine(props); // // 初始化模板引擎

        // 设置velocity资源加载方式为class
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        // 获取模板文件
        Template t = ve.getTemplate("templates/entity.java.vm");

        VelocityTemplateAnalyzer analyzer = new VelocityTemplateAnalyzer();

        analyzer.analyze(t);
    }
}
