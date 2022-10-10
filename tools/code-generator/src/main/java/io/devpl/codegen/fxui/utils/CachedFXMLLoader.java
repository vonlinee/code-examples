package io.devpl.codegen.fxui.utils;

import javafx.fxml.FXMLLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedFXMLLoader {

   static final Map<String, FXMLLoader> cachedFxmlLoaders = new ConcurrentHashMap<>();
}
