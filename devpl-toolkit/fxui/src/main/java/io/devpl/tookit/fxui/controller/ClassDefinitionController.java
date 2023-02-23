package io.devpl.tookit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.tookit.fxui.view.filestructure.FieldItem;
import io.devpl.tookit.fxui.view.filestructure.JavaFileStrucutreTreeView;
import io.devpl.tookit.fxui.view.filestructure.MethodItem;
import io.devpl.tookit.fxui.view.filestructure.TopLevelClassItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 类编辑器
 */
@FxmlLocation(location = "layout/class_definition.fxml", title = "类编辑")
public class ClassDefinitionController extends FxmlView {

    @FXML
    public BorderPane bopRoot;
    @FXML
    public SplitPane sppCenter;

    private JavaFileStrucutreTreeView jfsTreeView;

    private ScrollPane detailPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jfsTreeView = new JavaFileStrucutreTreeView();
        detailPane = new ScrollPane();

        final ViewLoader load = ViewLoader.load(ClassView.class);

        sppCenter.getItems().addAll(jfsTreeView, load.getRoot());
    }

    @FXML
    public void addDefaultClass(ActionEvent actionEvent) {
        TopLevelClassItem classItem = new TopLevelClassItem();
        classItem.setValue("Student");
        MethodItem methodItem = new MethodItem();
        methodItem.setValue("setName");
        classItem.addMethod(methodItem);
        FieldItem fieldItem = new FieldItem();
        fieldItem.setValue("name");
        classItem.addField(fieldItem);
        jfsTreeView.addClass(classItem);
    }
}
