package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.FxmlLocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.java.render.TopLevelClassRenderer;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlLocation(location = "static/fxml/class_definition.fxml")
public class ClassDefinitionController extends FxmlView {

    @FXML
    public CodeArea codePreview;
    @FXML
    public VBox vboxInputRoot;
    @FXML
    public VBox vboxClassBasicInfoRoot;
    @FXML
    public TextField txfClassName;
    @FXML
    public Accordion acdFieldMethodInfoRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acdFieldMethodInfoRoot.prefHeightProperty()
                .bind(vboxInputRoot.heightProperty()
                        .subtract(vboxClassBasicInfoRoot.heightProperty()));
    }

    @FXML
    public void add(ActionEvent actionEvent) {

    }
}
