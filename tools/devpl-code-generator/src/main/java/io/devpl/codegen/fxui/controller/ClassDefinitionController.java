package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.framework.mvc.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassDefinitionController extends FXController {

    @FXML
    public TitledPane tilpStaticFieldDef;
    @FXML
    public TitledPane tilpNonStaticFieldDef;
    @FXML
    public TitledPane tilpMethodDef;
    public BorderPane bopStaticFieldDef;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
