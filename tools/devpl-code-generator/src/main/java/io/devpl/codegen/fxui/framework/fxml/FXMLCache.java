package io.devpl.codegen.fxui.framework.fxml;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 包含FXML，根节点，Controller三者
 */
public class FXMLCache {

    // FXML URL，可以是本地文件，也可以是网络地址，确保能够用其构造一个URL对象
    private final String fxmlUrl;
    // 根节点
    private WeakReference<Object> rootNode;
    // FXML中定义的Controller
    private WeakReference<Object> controller;

    public FXMLCache(String fxmlUrl) {
        this.fxmlUrl = fxmlUrl;
    }

    public URL toURL() {
        try {
            return new URL(fxmlUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getRoot() {
        if (isCacheEmpty(rootNode)) {
            refresh();
        }
        return (T) rootNode;
    }

    @SuppressWarnings("unchecked")
    public <T> T getController() {
        if (isCacheEmpty(controller)) {
            refresh();
        }
        return (T) controller.get();
    }

    private boolean isCacheEmpty(WeakReference<Object> cache) {
        return cache == null || cache.get() == null;
    }

    private void refresh() {
        final FXMLLoader loader = new FXMLLoader(toURL());
        try {
            final Object newRootNode = loader.load();
            rootNode = new WeakReference<>(newRootNode);
            controller = new WeakReference<>(loader.getController());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
