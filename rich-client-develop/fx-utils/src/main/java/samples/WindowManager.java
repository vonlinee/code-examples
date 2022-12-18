package samples;

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

    private WindowManager() {
        windowStack = new Stack<>();
    }

    // 只能调用一次
    public static void setPrimaryStage(Stage primaryStage) {
        if (INSTANCE.windowStack.get(0) == null) {
            INSTANCE.windowStack.push(primaryStage);
        }
    }

    /**
     * 栈顶元素为最顶层窗口，第一个位置为子窗口
     * 需要同步
     */
    private final Stack<Stage> windowStack;

    public Stage currentStage() {
        return windowStack.lastElement();
    }

    public static WindowManager getInstance() {
        return INSTANCE;
    }

    /**
     * 子窗口，模态窗口Modality.WINDOW_MODAL
     * @return
     */
    public Stage createChildStage() {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(windowStack.lastElement());
        windowStack.push(stage);
        stage.setOnCloseRequest(event -> {
            windowStack.remove(stage);
        });
        return stage;
    }

    EventHandler<KeyEvent> escKeyHandler;

    public Stage getPrimaryStage() {
        return windowStack.firstElement();
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
        Stage stage = createChildStage();
        Scene scene = view.getScene();
        if (scene == null) {
            scene = new Scene(view);
            setHandlers(scene);
        }
        stage.setScene(scene);
        return stage;
    }

    public void closeWindow() {
        windowStack.lastElement().close();
    }

    public static Stage getStage(Parent view) {
        return INSTANCE.createWindow(view);
    }
}