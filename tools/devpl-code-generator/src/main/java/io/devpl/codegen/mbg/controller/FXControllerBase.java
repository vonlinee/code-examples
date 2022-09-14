package io.devpl.codegen.mbg.controller;

import io.devpl.codegen.mbg.view.Alerts;
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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaFX Controller的基类
 */
public abstract class FXControllerBase implements Initializable {
    private static final Logger _LOG = LoggerFactory.getLogger(FXControllerBase.class);

    private Stage primaryStage;
    private Stage dialogStage;

    private static final Map<FXMLPage, SoftReference<? extends FXControllerBase>> cacheNodeMap = new HashMap<>();

    /**
     * 加载FXML页面
     * @param title
     * @param fxmlPage
     * @param cache
     * @return
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
            SoftReference<FXControllerBase> softReference = new SoftReference<>(controller);
            cacheNodeMap.put(fxmlPage, softReference);
            return controller;
        } catch (IOException e) {
            _LOG.error(e.getMessage(), e);
            Alerts.showErrorAlert(e.getMessage());
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
