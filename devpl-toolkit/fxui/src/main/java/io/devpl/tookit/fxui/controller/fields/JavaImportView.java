package io.devpl.tookit.fxui.controller.fields;

import io.devpl.codegen.meta.ASTFieldParser;
import io.devpl.codegen.meta.FieldMetaData;
import io.devpl.codegen.meta.JavaParserUtils;
import io.devpl.tookit.fxui.editor.CodeMirrorEditor;
import io.devpl.tookit.fxui.editor.LanguageMode;
import io.devpl.tookit.fxui.model.FieldSpec;
import io.devpl.tookit.utils.FileUtils;
import io.devpl.tookit.utils.fx.FileChooserDialog;
import io.fxtras.mvc.FxmlLocation;
import io.fxtras.mvc.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.greenrobot.eventbus.Subscribe;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@FxmlLocation(location = "layout/fields/FieldsImportJavaView.fxml")
public class JavaImportView extends FxmlView {
    @FXML
    public BorderPane bopRoot;

    CodeMirrorEditor editor = CodeMirrorEditor.newInstance(LanguageMode.JAVA);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        bopRoot.setCenter(editor.getView());
    }

    @FXML
    public void chooseFile(ActionEvent actionEvent) {
        FileChooserDialog.showFileOpenDialog(getStage(actionEvent)).ifPresent(file -> {
            editor.setContent(FileUtils.readToString(file), true);
        });
    }

    @Subscribe
    public void parse(FieldImportEvent event) {
        try (StringReader sr = new StringReader(editor.getContent())) {
            JavaParserUtils.parse(sr, new ASTFieldParser()).map(this::convert)
                    .ifPresent(list -> event.getTableView().getItems().addAll(list));
        }
    }

    public List<FieldSpec> convert(List<FieldMetaData> fieldMetaDataList) {
        List<FieldSpec> list = new ArrayList<>(fieldMetaDataList.size());
        for (FieldMetaData fieldMetaData : fieldMetaDataList) {
            FieldSpec fieldSpec = new FieldSpec();
            fieldSpec.setFieldName(fieldMetaData.getName());
            fieldSpec.setFieldDescription(fieldMetaData.getDescription());
            list.add(fieldSpec);
        }
        return list;
    }
}
