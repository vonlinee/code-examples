package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;

/**
 * 测试类继承此类，实现createRoot()方法
 * 建议所有启动类以Test开头
 */
public abstract class TestApplication extends Application {

    /**
     * 初始宽高
     */
    protected double initialWidth = 600.0, initialHeight = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Region region = new Region();
        Scene scene = new Scene(region, initialWidth, initialHeight);
        primaryStage.setScene(scene);
        scene.setRoot(createRoot(primaryStage));
        primaryStage.show();
    }

    public abstract Parent createRoot(Stage primaryStage) throws Exception;

    public static void main(String[] args) {
        launch(args);
    }

    public URL getResourceAsURL(String filename) {
        return getClass().getResource(filename);
    }
}
