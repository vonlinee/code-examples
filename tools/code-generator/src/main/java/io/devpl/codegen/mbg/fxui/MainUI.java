package io.devpl.codegen.mbg.fxui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 本软件要求jkd版本大于1.8.0.40
 */
public class MainUI extends Application {

    private static final Logger _LOG = LoggerFactory.getLogger(MainUI.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    // JavaFX 11 运行此类不行，会提示缺少JavaFX运行时组件
    public static void main(String[] args) {
        launch(args);
    }
}
