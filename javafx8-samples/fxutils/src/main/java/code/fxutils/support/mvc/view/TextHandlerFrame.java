package code.fxutils.support.mvc.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TextHandlerFrame extends Application {

    private TextArea left;
    private TextArea right;
    private BorderPane root;
    private Scene scene;
    private SplitPane splitPane;

    private interface TextHandler {
        String handle(String from);
    }

    private void addButtonEventHandler(Button btn, TextHandler handler) {
        btn.setOnAction(event -> {
            right.setText(handler.handle(left.getText()));
        });
    }

    private void initStage(Stage stage) {
        stage.setTitle("文本处理工具");
        root = new BorderPane();
        scene = new Scene(root, 600, 450);

        splitPane = new SplitPane();
        splitPane.setDividerPositions(0.4, 0.6);
        root.setCenter(splitPane);
        splitPane.getItems().add(left = new TextArea());

        VBox vBox = new VBox();
        Button b11 = new Button("驼峰转下划线");
        addButtonEventHandler(b11, from -> {
            String[] lines = from.split("\n");
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < lines.length; i++) {
                String trim = lines[i].trim();

            }
            return result.toString();
        });

        vBox.getChildren().add(b11);

        splitPane.getItems().add(vBox);
        splitPane.getItems().add(right = new TextArea());

        left.setFont(Font.font(20));
        right.setFont(Font.font(20));

        //TOP
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: green");
        TextField textField = new TextField("输入input.txt位置");
        hBox.getChildren().add(textField);
        root.setTop(hBox);
        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initStage(primaryStage);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    }


    private void initMemberListener() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
