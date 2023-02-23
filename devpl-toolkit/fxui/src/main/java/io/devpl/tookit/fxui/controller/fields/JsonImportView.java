package io.devpl.tookit.fxui.controller.fields;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.fxmisc.richtext.CodeArea;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlLocation(location = "layout/fields/ImportFieldsJSONView.fxml")
public class JsonImportView extends FxmlView {

    @FXML
    public CodeArea content;
    @FXML
    public ChoiceBox<String> chbJsonSpec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chbJsonSpec.getItems().addAll("JSON", "JSON5", "HJSON");
    }
}
