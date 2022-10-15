package io.devpl.codegen.ui.fxui.controller;

import io.devpl.codegen.ui.fxui.utils.AlertDialog;
import io.devpl.codegen.ui.fxui.utils.FXMLHelper;
import io.devpl.codegen.ui.fxui.utils.FXMLPage;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Code designers should also be careful when using the Stage within a controller.
 * Typically, the controller is responsible only for updating the model and view,
 * and is shouldn’t really be responsible for the Window lifecycle.
 * This responsibility more comfortably fits with whichever class created the Stage in the first place.
 */
public abstract class FXController implements EventTarget, Initializable {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final FXEventBus BUS = new FXEventBus();

    private final ControllerEventDispatcher dispatcher = new ControllerEventDispatcher(this);

    /**
     * 发布事件
     * @param event         发送的事件
     * @param matcher       过滤器
     * @param useThisSource 是否将当前Controller作为事件源
     */
    public final void publish(Event event, Function<EventTarget, Boolean> matcher, boolean useThisSource) {
        if (useThisSource) {
            event = event.copyFor(this, event.getTarget());
        }
        BUS.publish(event, matcher);
    }

    public final void publish(Event event, Function<EventTarget, Boolean> matcher) {
        publish(event, target -> true, true);
    }

    public final void publish(Event event) {
        publish(event, target -> true, true);
    }

    private Stage primaryStage;
    private Stage dialogStage;

    private static final Map<FXMLPage, SoftReference<? extends FXController>> cacheNodeMap = new HashMap<>();

    public FXController loadFXMLPage(String title, FXMLPage fxmlPage, boolean cache) {
        SoftReference<? extends FXController> parentNodeRef = cacheNodeMap.get(fxmlPage);
        if (cache && parentNodeRef != null) {
            return parentNodeRef.get();
        }


        FXMLLoader loader = FXMLHelper.createFXMLLoader(fxmlPage);
        Parent loginNode;
        try {
            loginNode = loader.load();
            FXController controller = loader.getController();
            // fix bug: 嵌套弹出时会发生dialogStage被覆盖的情况
            Stage tmpDialogStage = new Stage();
            tmpDialogStage.setTitle(title);
            tmpDialogStage.initModality(Modality.APPLICATION_MODAL);
            tmpDialogStage.initOwner(getPrimaryStage());
            tmpDialogStage.setScene(new Scene(loginNode));
            tmpDialogStage.setMaximized(false);
            tmpDialogStage.setResizable(false);
            tmpDialogStage.show();
            controller.setDialogStage(tmpDialogStage);
            // put into cache map
            SoftReference<FXController> softReference = new SoftReference<>(controller);
            cacheNodeMap.put(fxmlPage, softReference);
            return controller;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            AlertDialog.showError(e.getMessage());
        }
        return null;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void showDialogStage() {
        if (dialogStage != null) {
            dialogStage.show();
        }
    }

    public void closeDialogStage() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    /**
     * 订阅事件
     * @param eventType
     * @param eventHandler
     * @param <T>
     */
    public final <T extends Event> void addEventHandler(
            final EventType<T> eventType,
            final EventHandler<? super T> eventHandler) {
        BUS.register(eventType, this, eventHandler);
    }

    public void fireEvent() {

    }

    private ControllerEventDispatcher getInternalEventDispatcher() {
        return dispatcher;
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(dispatcher);
    }
}