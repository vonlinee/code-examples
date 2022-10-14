package io.devpl.codegen.ui.fxui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class JavaFileGenerationController implements Initializable {

    @FXML
    public TableView beanInfoTableView;
    @FXML
    public TableView fxBeanInfoTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
