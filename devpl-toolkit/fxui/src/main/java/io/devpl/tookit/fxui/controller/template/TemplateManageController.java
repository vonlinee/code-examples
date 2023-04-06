package io.devpl.tookit.fxui.controller.template;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.editor.CodeMirrorEditor;
import io.devpl.tookit.fxui.editor.LanguageMode;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlLocation(location = "layout/template/template_manage.fxml")
public class TemplateManageController extends FxmlView {

    @FXML
    public VBox vboxLeft;
    @FXML
    public VBox vboxRight;

    CodeMirrorEditor codeEditor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (codeEditor == null) {
            codeEditor = new CodeMirrorEditor();
            codeEditor.setAutoCompleteFunction(s -> {
                List<String> a = new ArrayList<>();
                a.add("when");
                return a.stream().filter(value -> value.startsWith(s)).collect(Collectors.toList());
            });
            codeEditor.init(
                    () -> codeEditor.setMode(LanguageMode.VELOCITY),
                    () -> codeEditor.setTheme("xq-light"));
            vboxLeft.getChildren().add(codeEditor.getView());
        }
    }

    @FXML
    public void parseTemplate(MouseEvent mouseEvent) {

    }



    public void m1() throws IOException {
        // 初始化模板引擎
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        final VelocityTemplateAnalyzer analyzer = new VelocityTemplateAnalyzer();
        String text = "";

        Path path = Path.of("D:/Temp/1.vm");

        Files.deleteIfExists(path);

        Path templateFile = Files.write(path, text.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        // 获取模板文件
        Template t = ve.getTemplate(templateFile.toAbsolutePath().toString());

        analyzer.analyze(t);
    }
}
