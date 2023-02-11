package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 字段元信息管理
 */
@FxmlLocation(location = "static/fxml/MetaFieldImport.fxml", title = "导入字段元信息")
public class MetaFieldImportController extends FxmlView {

    @FXML
    public TabPane tbpImportContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbpImportContent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            newValue.getContent().requestFocus();
        });
    }
}
