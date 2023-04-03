package io.devpl.tookit.fxui.controller.fields;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.editor.CodeEditor;
import io.devpl.tookit.fxui.model.FieldSpec;
import io.devpl.tookit.fxui.view.json.JSONTreeView;
import io.devpl.tookit.utils.FileUtils;
import io.devpl.tookit.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

@FxmlLocation(location = "layout/fields/ImportFieldsJSONView.fxml")
public class JsonImportView extends FxmlView {

    @FXML
    public CodeEditor content;
    @FXML
    public ChoiceBox<String> chbJsonSpec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chbJsonSpec.getItems().addAll("JSON", "JSON5", "HJSON");
        chbJsonSpec.getSelectionModel().select(0);
    }

    /**
     * 解析字段
     *
     * @param event
     */
    @Subscribe
    public void parseFieldsFromInput(FieldImportEvent event) {
        try {
            List<FieldSpec> list = extractFieldsFromJson(content.getText());
            publish("AddFields", list);
        } catch (Exception exception) {
            Alerts.exception("解析异常", exception).showAndWait();
            log.error("解析异常", exception);
        }
    }

    Gson gson = new Gson();

    /**
     * 解析JSON
     *
     * @param input json文本
     * @return 字段列表
     */
    private List<FieldSpec> extractFieldsFromJson(String input) {
        List<FieldSpec> list = new ArrayList<>();
        JsonElement jsonElement = gson.fromJson(input, JsonElement.class);
        fill(list, jsonElement);
        return list;
    }

    /**
     * 提取所有的Key，不提取值
     *
     * @param fieldList
     * @param jsonElement
     */
    private void fill(List<FieldSpec> fieldList, JsonElement jsonElement) {
        if (jsonElement == null) {
            return;
        }
        if (jsonElement.isJsonObject()) {
            JsonObject jobj = jsonElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jobj.entrySet()) {
                final JsonElement value = entry.getValue();
                if (value.isJsonNull() || value.isJsonPrimitive()) {
                    final FieldSpec metaField = new FieldSpec();
                    metaField.setFieldName(entry.getKey());
                    fieldList.add(metaField);
                } else {
                    fill(fieldList, value);
                }
            }
        } else if (jsonElement.isJsonArray()) {
            final JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray.asList()) {
                fill(fieldList, element);
            }
        }
    }

    JSONTreeView jsonTreeView = new JSONTreeView();

    @FXML
    public void showJsonTree(ActionEvent actionEvent) {
        String text = content.getText();
        if (StringUtils.hasNotText(text)) {
            return;
        }
        JsonElement jsonElement = gson.fromJson(text, JsonElement.class);
        jsonTreeView.addRootJson(jsonElement);

        Scene scene;
        if ((scene = jsonTreeView.getScene()) == null) {
            scene = new Scene(jsonTreeView, 600, 600);
        }
        Stage stage;
        if (Objects.isNull(stage = (Stage) scene.getWindow())) {
            stage = new Stage();
            stage.setScene(scene);
        }
        stage.show();
    }

    FileChooser fileChooser = new FileChooser();

    /**
     * 选择JSON文件
     *
     * @param actionEvent 事件
     */
    @FXML
    public void chooseJsonFile(ActionEvent actionEvent) {
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("json", ".json"));
        File file = fileChooser.showOpenDialog(getStage(actionEvent));
        if (file != null) {
            content.clear();
            content.appendText(FileUtils.readToString(file));
        }
    }
}
