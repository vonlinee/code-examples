package io.devpl.toolkit.fxui.framework.core;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * 窗口管理器
 * <a href="https://www.c-sharpcorner.com/code/2654/javafx-managing-multiple-stages.aspx">...</a>
 */
public class WindowManager {

    private static final WindowManager INSTANCE = new WindowManager();

    /**
     * 栈顶元素为最顶层窗口
     */
    private final Stack<Stage> windows;

    private WindowManager() {
        windows = new Stack<>();
    }

    public void setMainWindow(Stage stage) {
        windows.push(stage);
    }

    public Stage getCurrentWindow() {
        return windows.lastElement();
    }

    public static WindowManager getInstance() {
        return INSTANCE;
    }

    public Stage newWindow() {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(windows.lastElement());
        windows.push(stage);
        stage.setOnCloseRequest(event -> {
            windows.remove(stage);
        });
        return stage;
    }

    EventHandler<KeyEvent> escKeyHandler;

    public Stage getMainWindow() {
        return windows.firstElement();
    }

    private void setHandlers(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, getEscKeyHandler());
    }

    public EventHandler<KeyEvent> getEscKeyHandler() {
        if (escKeyHandler == null) {
            escKeyHandler = event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    closeWindow();
                }
            };
        }
        return escKeyHandler;
    }

    public Stage createWindow(Parent view) {
        Stage stage = newWindow();
        Scene scene = view.getScene();
        if (scene == null) {
            scene = new Scene(view);
            setHandlers(scene);
        }
        stage.setScene(scene);
        return stage;
    }

    public void closeWindow() {
        windows.lastElement().close();
    }

    public static Stage getStage(Parent view) {
        return INSTANCE.createWindow(view);
    }
}