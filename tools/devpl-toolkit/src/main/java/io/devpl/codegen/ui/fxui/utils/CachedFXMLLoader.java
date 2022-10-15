package io.devpl.codegen.ui.fxui.utils;

import javafx.fxml.FXMLLoader;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CachedFXMLLoader {

    static final Map<String, FXMLLoader> cachedFxmlLoaders = new ConcurrentHashMap<>();

    private WeakHashMap<String, FXMLLoader> cache = new WeakHashMap<>();


    public static void main(String[] args) {
        FXMLLoader loader = new FXMLLoader();
        loader.load();
    }
}
