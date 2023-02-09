package scenegraph.node.parent.control.textarea;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            TextArea area = new TextArea();


            area.setOnDragDetected(event -> {

                area.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                System.out.println(event);
            });

            TextField textField = new TextField();

            area.setOnDragOver(new DragOverEvent(textField));
            area.setOnDragDropped(new DragDroppedEvent(textField));

            Scene scene = new Scene(area, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created by loongshawn 2016/11/3.
     * <p>
     * NOTE 文件拖到控件上方事件
     */
    public class DragOverEvent implements EventHandler<DragEvent> {

        private TextField textField;

        public DragOverEvent(TextField textField) {
            this.textField = textField;
        }

        public void handle(DragEvent event) {
            if (event.getGestureSource() != textField) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        }
    }

    /**
     * Created by loongshawn 2016/11/3.
     * <p>
     * NOTE 文件拖到控件上方，鼠标松开事件
     */
    public class DragDroppedEvent implements EventHandler<DragEvent> {

        private TextField textField;

        public DragDroppedEvent(TextField textField) {
            this.textField = textField;
        }

        public void handle(DragEvent event) {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                try {
                    File file = dragboard.getFiles().get(0);
                    if (file != null) {
                        textField.setText(file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
