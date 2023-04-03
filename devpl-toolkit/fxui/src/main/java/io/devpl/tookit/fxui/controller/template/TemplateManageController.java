package io.devpl.tookit.fxui.controller.template;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.editor.CodeEditor;
import io.devpl.tookit.utils.FileUtils;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@FxmlLocation(location = "layout/template/template_manage.fxml")
public class TemplateManageController extends FxmlView {

    @FXML
    public CodeEditor templateContent;

    @FXML
    public void parseTemplate(MouseEvent mouseEvent) throws IOException {
        // 初始化模板引擎
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        final VelocityTemplateAnalyzer analyzer = new VelocityTemplateAnalyzer();
        String text = templateContent.getText();

        Path path = Path.of("D:/Temp/1.vm");

        Files.deleteIfExists(path);

        Path templateFile = Files.write(path, text.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);



        // 获取模板文件
        Template t = ve.getTemplate(templateFile.toAbsolutePath().toString());

        analyzer.analyze(t);
    }
}
