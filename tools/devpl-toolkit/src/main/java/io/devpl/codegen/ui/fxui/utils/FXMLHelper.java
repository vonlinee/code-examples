package io.devpl.codegen.ui.fxui.utils;

import javafx.fxml.FXMLLoader;
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
import java.util.Optional;

public class FXMLHelper {

    private static final Logger LOG = LoggerFactory.getLogger(FXMLHelper.class);

    private static final Map<String, String> fxmls = new HashMap<>();

    public static Optional<Parent> load(String fxmlLocation) {
        Parent parent;
        try {
            parent = createFXMLLoader(fxmlLocation).load();
        } catch (IOException exception) {
            LOG.error("load fxml[{}] failed, {}", fxmlLocation, exception);
            return Optional.empty();
        }
        return Optional.ofNullable(parent);
    }

    public static FXMLLoader createFXMLLoader(FXMLPage page) {
        return new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource(page.getFxml()));
    }

    public static FXMLLoader createFXMLLoader(String pathname) {
        return new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource(pathname));
    }

    /**
     * FXML页面对应的Controller缓存
     */
    private static final Map<FXMLPage, SoftReference<?>> cacheNodeMap = new HashMap<>();

    /**
     * @param primaryStage
     * @param title
     * @param fxmlPage
     * @param cache
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T showChildStage(Stage primaryStage, String title, FXMLPage fxmlPage, boolean cache) {
        // 从缓存中获取
        SoftReference<?> parentNodeRef = cacheNodeMap.get(fxmlPage);
        if (cache && parentNodeRef != null) {
            return (T) parentNodeRef.get();
        }
        // 重新加载FXML，获取Controller实例
        FXMLLoader loader = createFXMLLoader(fxmlPage);
        try {
            Object controller = loader.getController();
            // 子窗口
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.show();
            // put into cache map
            SoftReference<?> softReference = new SoftReference<>(controller);
            cacheNodeMap.put(fxmlPage, softReference);
            return (T) controller;
        } catch (IOException e) {
            AlertDialog.showError(e.getMessage());
        }
        return null;
    }
}
