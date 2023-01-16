package io.devpl.toolkit.fxui.utils;

import io.devpl.fxtras.Alerts;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class FXMLLoaderUtils {

    public static Parent load(URL location) {
        try {
            return FXMLLoader.load(location);
        } catch (IOException e) {
            Alerts.exception("加载FXML失败", e);
            return null;
        }
    }
}
