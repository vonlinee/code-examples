package io.devpl.tookit.fxui.view.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import de.marhali.json5.Json5;
import io.devpl.tookit.utils.fx.FileChooserDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * JSON 数据可视化
 */
public class JSONTreeViewApplication extends Application {

    Gson gson = new Gson();

    Json5 json5 = new Json5();

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        ToolBar toolBar = new ToolBar();
        root.setTop(toolBar);

        JSONTreeView jsonTreeView = new JSONTreeView();
        root.setCenter(jsonTreeView);

        Button btn = new Button("选择文件");
        btn.setOnAction(event -> {
        	
        	FileChooserDialog fcd = new FileChooserDialog();
        	
        	
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\Users\\Von\\Desktop\\"));
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("json", ".json"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try (JsonReader reader = gson.newJsonReader(new FileReader(file))) {
                    JsonElement element = gson.fromJson(reader, JsonElement.class);
                    jsonTreeView.addRootJson(element);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        toolBar.getItems().add(btn);

        primaryStage.setScene(new Scene(root, 300, 250));

        primaryStage.show();
    }
}
