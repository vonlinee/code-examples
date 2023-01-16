package io.devpl.toolkit.fxui.controller;

import io.devpl.toolkit.framework.mvc.AbstractViewController;
import io.devpl.toolkit.framework.mvc.FxmlView;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.java.render.TopLevelClassRenderer;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView(location = "static/fxml/class_definition.fxml")
public class ClassDefinitionController extends AbstractViewController {

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

    TopLevelClass classInfo = new TopLevelClass("");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acdFieldMethodInfoRoot.prefHeightProperty()
                              .bind(vboxInputRoot.heightProperty().subtract(vboxClassBasicInfoRoot.heightProperty()));
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        classInfo.addImportedType("java.util.List");
        classInfo.setStatic(false);
        classInfo.setVisibility(JavaVisibility.PUBLIC);
        classInfo.addField(new Field("name", FullyQualifiedJavaType.getStringInstance()));

        TopLevelClassRenderer topLevelClassRenderer = new TopLevelClassRenderer();

        codePreview.appendText(topLevelClassRenderer.render(classInfo));
    }
}
