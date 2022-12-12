package io.devpl.codegen.fxui.framework.fxml;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 包含FXML，根节点，Controller三者
 */
public final class FXMLCache {

    private final FXMLLoader loader;
    // 根节点
    private WeakReference<Object> rootNodeCache = new WeakReference<>(null);
    // FXML中定义的Controller
    private WeakReference<Object> controllerCache = new WeakReference<>(null);

    private final Map<Class<?>, Object> controllerCacheMap = new ConcurrentHashMap<>();

    public FXMLCache(FXMLLoader loader) {
        this.loader = loader;
    }

    public URL getLocationURL() {
        return loader.getLocation();
    }

    public FXMLLoader getFXMLLoader() {
        if (rootNodeCache.get() == null || controllerCache.get() == null) {
            try {
                final Object rootNode = loader.load();
                this.rootNodeCache = new WeakReference<>(rootNode);
                this.controllerCache = new WeakReference<>(loader.getController());
            } catch (IOException e) {
                throw new RuntimeException("failed to load fxml: " + e);
            }
        }
        return loader;
    }
}
