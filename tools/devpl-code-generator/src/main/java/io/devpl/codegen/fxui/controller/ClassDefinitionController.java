package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.common.control.SimpleTableView;
import io.devpl.codegen.fxui.framework.JFX;
import io.devpl.codegen.fxui.framework.mvc.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassDefinitionController extends FXController {

    @FXML
    public TitledPane tilpStaticFieldDef;
    @FXML
    public TitledPane tilpNonStaticFieldDef;
    @FXML
    public TitledPane tilpMethodDef;
    @FXML
    public BorderPane bopStaticFieldDef;

    // 非静态字段定义
    // private final SimpleTableView simpleTableView1 = new SimpleTableView();
    // private final SimpleTableView simpleTableView2 = new SimpleTableView();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // final HBox hbxTableOperation = new HBox();
    }
}
