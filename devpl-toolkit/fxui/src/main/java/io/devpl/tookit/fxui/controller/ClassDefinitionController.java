package io.devpl.tookit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.view.filestructure.JavaFileStrucutreTreeView;
import javafx.fxml.FXML;
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

    private JavaFileStrucutreTreeView jfxTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bopRoot.setCenter(jfxTreeView = new JavaFileStrucutreTreeView());
    }
}
