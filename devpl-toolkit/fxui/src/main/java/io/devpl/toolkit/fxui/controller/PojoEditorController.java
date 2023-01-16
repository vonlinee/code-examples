package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.toolkit.fxui.utils.StringUtils;
import io.devpl.toolkit.test.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlLocation(location = "static/fxml/pojo_editor.fxml")
public class PojoEditorController extends FxmlView {

    @FXML
    public TextArea txaContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void parseColumns(ActionEvent actionEvent) {
        String text = txaContent.getText();
        if (StringUtils.isBlank(text)) {
            return;
        }
        final List<String> columns = Utils.getSelectColumns(text);
        txaContent.setText(String.join("\n", columns));
    }
}
