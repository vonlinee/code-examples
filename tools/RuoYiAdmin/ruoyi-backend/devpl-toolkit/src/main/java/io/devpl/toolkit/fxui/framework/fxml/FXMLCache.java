package io.devpl.toolkit.fxui.framework.fxml;

import javafx.fxml.FXMLLoader;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * 包含FXML，根节点，Controller三者
 */
public final class FXMLCache {

    // FXML位置
    private final URL location;
    private WeakReference<Object> rootNodeCache;
    private WeakReference<Object> controllerCache;

    public FXMLCache(URL location) {
        this.location = location;
    }

    public FXMLLoader getFXMLLoader() {
        return new FXMLLoader(location);
    }
}
