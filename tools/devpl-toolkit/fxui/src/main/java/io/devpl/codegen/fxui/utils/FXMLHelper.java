package io.devpl.codegen.fxui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class FXMLHelper {

    private static final Logger LOG = LoggerFactory.getLogger(FXMLHelper.class);

    private static final WeakHashMap<String, FxmlLoadResult> cache = new WeakHashMap<>();

    static {
        FXMLLoader loader;
        for (Map.Entry<String, URI> entry : FXMLFinder.findFromClasspath().entrySet()) {
            String fxmlRelativePath = entry.getKey();
            loader = new FXMLLoader();
            try {
                loader.setCharset(StandardCharsets.UTF_8);
                loader.setLocation(entry.getValue().toURL());
                loader.load();
            } catch (Exception e) {
                LOG.error("failed to load fxml {}, {}", entry.getValue(), e);
                continue;
            }
            FxmlLoadResult result = new FxmlLoadResult(loader.getRoot(), loader.getController(), entry.getValue().toString());
            cache.put(fxmlRelativePath, result);
        }
    }

    /**
     * 先从缓存中获取，缓存中没有再重新从硬盘加载
     * @param fxmlLocation
     * @return
     */
    public static FxmlLoadResult loadFxml(String fxmlLocation) {
        fxmlLocation = resolveFxmlName(fxmlLocation);
        FxmlLoadResult result = cache.get(fxmlLocation);
        if (result == null) {
            FXMLLoader loader = createFXMLLoader(fxmlLocation);
            try {
                loader.load();
            } catch (Exception exception) {
                LOG.error("failed to load fxml [{}]", fxmlLocation, exception);
            }
            result = new FxmlLoadResult(loader.getRoot(), loader.getController(), loader.getLocation().toExternalForm());
            cache.put(fxmlLocation, result);
        }
        return result;
    }

    /**
     * @param fxmlLocation 格式要求：xxx/xxx.fxml
     * @return
     */
    private static String resolveFxmlName(String fxmlLocation) {
        if (fxmlLocation.startsWith("/") || fxmlLocation.startsWith("\\")) {
            fxmlLocation = fxmlLocation.substring(1);
        }
        return fxmlLocation.replace("\\", "/");
    }

    public static Optional<Parent> load(String fxmlLocation) {
        fxmlLocation = resolveFxmlName(fxmlLocation);
        Parent parent;
        try {
            parent = createFXMLLoader(fxmlLocation).load();
        } catch (Exception exception) {
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
            Alerts.error(e.getMessage());
        }
        return null;
    }
}
