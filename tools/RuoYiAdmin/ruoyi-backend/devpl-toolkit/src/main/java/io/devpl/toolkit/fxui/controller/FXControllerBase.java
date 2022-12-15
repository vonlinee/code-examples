package io.devpl.toolkit.fxui.controller;

import io.devpl.toolkit.fxui.framework.Alerts;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.framework.mvc.FXController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * JFX Controller的基类
 */
public abstract class FXControllerBase extends FXController implements Initializable {

    // 基础窗口
    private Stage primaryStage;
    // 弹窗窗口
    private Stage dialogStage;

    private static final Map<FXMLPage, SoftReference<? extends FXControllerBase>> cacheNodeMap = new HashMap<>();

    /**
     * 加载FXML页面
     * @param title    标题
     * @param fxmlPage FXML页面
     * @param cache    是否缓存
     * @return 控制器类
     */
    public FXControllerBase loadFXMLPage(String title, FXMLPage fxmlPage, boolean cache) {
        SoftReference<? extends FXControllerBase> parentNodeRef = cacheNodeMap.get(fxmlPage);
        if (cache && parentNodeRef != null) {
            return parentNodeRef.get();
        }
        URL skeletonResource = Thread.currentThread().getContextClassLoader().getResource(fxmlPage.getFxml());
        FXMLLoader loader = new FXMLLoader(skeletonResource);
        Parent loginNode;
        try {
            loginNode = loader.load();
            FXControllerBase controller = loader.getController();
            // fix bug: 嵌套弹出时会发生dialogStage被覆盖的情况
            Stage tmpDialogStage =
                    JFX.newStage(title, getPrimaryStage(), Modality.APPLICATION_MODAL, new Scene(loginNode), true);
            tmpDialogStage.setMaximized(false);
            tmpDialogStage.show();
            controller.setDialogStage(tmpDialogStage);
            // put into cache map
            SoftReference<FXControllerBase> softReference = new SoftReference<>(controller);
            cacheNodeMap.put(fxmlPage, softReference);
            return controller;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            Alerts.error(e.getMessage()).showAndWait();
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
}
