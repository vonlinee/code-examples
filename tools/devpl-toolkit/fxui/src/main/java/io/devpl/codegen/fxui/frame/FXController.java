package io.devpl.codegen.fxui.frame;

import io.devpl.codegen.fxui.utils.Alerts;
import io.devpl.codegen.fxui.utils.FXMLPage;
import io.devpl.codegen.fxui.utils.FXMLHelper;
import org.greenrobot.eventbus.ext.EventBus;
import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;
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

/**
 * Code designers should also be careful when using the Stage within a controller.
 * Typically, the controller is responsible only for updating the model and view,
 * and is shouldn’t really be responsible for the Window lifecycle.
 * This responsibility more comfortably fits with whichever class created the Stage in the first place.
 */
public abstract class FXController implements EventTarget, Initializable {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * 事件总线
     */
    private static final EventBus BUS = EventBus.getDefault();

    private final ControllerEventDispatcher dispatcher = new ControllerEventDispatcher(this);

    private Stage primaryStage;
    private Stage dialogStage;

    public FXController() {
        BUS.register(this);
    }

    public final void post(Object eventType) {
        BUS.post(eventType);
    }

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
            Alerts.error(e.getMessage());
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

    private ControllerEventDispatcher getInternalEventDispatcher() {
        return dispatcher;
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(dispatcher);
    }
}