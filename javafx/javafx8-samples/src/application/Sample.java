package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 新建Application时，复制此类的代码即可
 * 如果使用Idea，可以新建Code Template，将此类的内容作为模板
 */
public class Sample extends Application {

    protected double initialWidth = 500.0;
    protected double initialHeight = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建根节点
     * @return Parent
     * @throws Exception Exception
     */
    public Parent createRoot() throws Exception {
        AnchorPane root = new AnchorPane();

        Button btn = new Button("Button");
        return root;
    }
}
