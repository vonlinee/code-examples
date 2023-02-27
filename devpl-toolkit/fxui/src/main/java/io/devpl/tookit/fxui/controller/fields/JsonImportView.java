package io.devpl.tookit.fxui.controller.fields;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.model.FieldSpec;
import io.devpl.tookit.fxui.view.json.JSONTreeView;
import io.devpl.tookit.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        final Stage stage = new Stage();
        stage.setScene(new Scene(jsonTreeView, 600, 600));
        stage.show();
    }

    /**
     * 选择JSON文件
     *
     * @param actionEvent
     */
    @FXML
    public void chooseJsonFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("json", ".json"));
        File file = fileChooser.showOpenDialog(getStage(actionEvent));
        if (file != null) {
            try (JsonReader reader = gson.newJsonReader(new FileReader(file))) {
                JsonElement element = gson.fromJson(reader, JsonElement.class);
                jsonTreeView.addRootJson(element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
