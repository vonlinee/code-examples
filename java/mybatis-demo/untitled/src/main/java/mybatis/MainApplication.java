package mybatis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        SplitPane splitPane = new SplitPane();

        VBox vBox = new VBox();
        splitPane.getItems().add(vBox);

        TextField textField = new TextField();
        Button btn = new Button("Evaluate");
        TextArea textArea = new TextArea();

        btn.setOnAction(event -> {
            String text = textField.getText();
            if (text != null && text.length() != 0) {
                Object value = LiteralValue.getValue(text);

                StringBuilder sb = new StringBuilder();
                sb.append(value.getClass()).append("\n");
                sb.append(value);

                textArea.setText(sb.toString());
            }
        });

        HBox hBox = new HBox(textField, btn);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(textArea);

        MapperStatementTable table = new MapperStatementTable();

        VBox vBox1 = new VBox(table);
        splitPane.getItems().add(vBox1);


        final Scene scene = new Scene(splitPane, 500, 400);//创建场景并加载节点容器组

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
