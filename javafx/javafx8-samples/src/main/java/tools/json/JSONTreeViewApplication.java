package tools.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tools.file.FileChooserDialog;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

/**
 * JSON 数据可视化
 */
public class JSONTreeViewApplication extends Application {

    Gson gson = new Gson();

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        ToolBar toolBar = new ToolBar();
        root.setTop(toolBar);

        JSONTreeView jsonTreeView = new JSONTreeView();
        root.setCenter(jsonTreeView);

        Button btn = new Button("选择文件");
        btn.setOnAction(event -> {
        	
        	FileChooserDialog dialog = new FileChooserDialog();
        	dialog.setInitialDirectory(new File("D:/Temp"));
        	
        	Optional<ButtonType> optional = dialog.showAndWait();
        	optional.ifPresent(btnType -> {
        		if (btnType == ButtonType.APPLY) {
        			
        			File file = dialog.getSelectedFile();
                    if (file != null) {
                        try (JsonReader reader = gson.newJsonReader(new FileReader(file))) {
                            JsonElement element = gson.fromJson(reader, JsonElement.class);
                            jsonTreeView.addRootJson(element);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
				}
        	});
        });
        toolBar.getItems().add(btn);

        primaryStage.setScene(new Scene(root, 300, 250));

        primaryStage.show();
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}
