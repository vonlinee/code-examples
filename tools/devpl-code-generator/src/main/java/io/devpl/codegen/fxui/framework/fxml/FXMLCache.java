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
public final class FXMLCache {

    // FXML URL，可以是本地文件，也可以是网络地址，确保能够用其构造一个URL对象
    private final FXMLLoader loader;
    // 根节点
    private WeakReference<Object> rootNode;
    // FXML中定义的Controller
    private WeakReference<Object> controller;

    public FXMLCache(FXMLLoader loader) {
        this.loader = loader;
    }

    public URL getLocationURL() {
        return loader.getLocation();
    }

    public FXMLLoader getFXMLLoader() {
        return loader;
    }
}
