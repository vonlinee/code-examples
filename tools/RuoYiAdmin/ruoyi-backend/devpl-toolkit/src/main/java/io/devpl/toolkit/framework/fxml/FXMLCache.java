package io.devpl.toolkit.framework.fxml;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * 包含FXML，根节点，Controller三者
 */
public class FXMLCache {

    // FXML位置
    private final URL location;
    private ControllerFactory factory;
    private WeakReference<Object> rootNodeCache;
    private WeakReference<Object> controllerCache;

    public FXMLCache(URL location) {
        this.location = location;
    }

    public void setControllerFactory(ControllerFactory factory) {
        this.factory = factory;
    }

    public Object getController() {
        Object controller;
        if (controllerCache == null || (controller = controllerCache.get()) == null) {
            loadFXML();
            return controllerCache.get();
        }
        return controller;
    }

    @SuppressWarnings("unchecked")
    public <T> T getRoot() {
        T rootNode = null;
        if (controllerCache == null || (rootNodeCache.get()) == null) {
            loadFXML();
            rootNode = (T) rootNodeCache.get();
        }
        return rootNode;
    }

    private void loadFXML() {
        final FXMLLoader loader = new FXMLLoader(this.location);
        loader.setControllerFactory(this.factory);
        try {
            rootNodeCache = new WeakReference<>(loader.load());
            controllerCache = new WeakReference<>(loader.getController());
        } catch (IOException e) {
            throw new RuntimeException("failed to load fxml [" + this.location + "]", e);
        }
    }
}
