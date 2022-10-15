package io.devpl.codegen.ui.fxui.controller;

import io.devpl.codegen.ui.fxui.model.BeanInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class JavaFileGenerationController implements Initializable {

    @FXML
    public TableView<BeanInfo> beanInfoTableView;
    @FXML
    public TableView<BeanInfo> fxBeanInfoTableView;
    @FXML
    public Button addRowBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
