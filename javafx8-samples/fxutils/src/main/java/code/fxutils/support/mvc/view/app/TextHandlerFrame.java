package code.fxutils.support.mvc.view.app;

import code.fxutils.support.mvc.view.Signal;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Signal(flag = true)
public class TextHandlerFrame extends Stage {

    private TextArea left;
    private TextArea right;

    private BorderPane root;
    private Scene scene;

    private SplitPane splitPane;

    public TextHandlerFrame() {
        setTitle("文本处理工具");
        root = new BorderPane();
        scene = new Scene(root, 600, 450);

        splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5);
        root.setCenter(splitPane);
        splitPane.getItems().add(left = new TextArea());
        splitPane.getItems().add(right = new TextArea());
        //TOP
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: green");
        TextField textField = new TextField("输入input.txt位置");
        hBox.getChildren().add(textField);
        root.setTop(hBox);
        //Bottom
        FlowPane flowPane = new FlowPane();

        Button b1 = new Button("AAA");
        Button b2 = new Button("BBB");
        flowPane.getChildren().addAll(b1, b2);
        root.setBottom(flowPane);
        setScene(scene);
    }

    private void method() {

    }

}
