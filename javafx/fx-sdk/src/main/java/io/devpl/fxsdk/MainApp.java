package io.devpl.fxsdk;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
    public static String mainViewID = "MainView";
    public static String mainViewRes = "MainView.fxml";

    public static String loginViewID = "LoginView";
    public static String loginViewRes = "LoginView.fxml";

    //新建一个StageController控制器
    private final StageController stageController = new StageController();

    @Override
    public void start(Stage primaryStage) {
        //将主舞台交给控制器处理
        stageController.setPrimaryStage("primaryStage", primaryStage);
        //加载两个舞台，每个界面一个舞台
        stageController.loadStage(loginViewID, loginViewRes);
        stageController.loadStage(mainViewID, mainViewRes);

        WindowManager.setPrimaryStage(primaryStage);
        //显示MainView舞台
        stageController.setStage(mainViewID);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
