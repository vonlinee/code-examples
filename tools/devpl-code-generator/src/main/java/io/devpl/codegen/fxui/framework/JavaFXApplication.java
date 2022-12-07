package io.devpl.codegen.fxui.framework;

import io.devpl.codegen.fxui.framework.fxml.FXMLScanner;
import javafx.application.Application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class JavaFXApplication extends Application {

    private final Map<String, String> fxmlMappings = new HashMap<>();

    @Override
    public void init() throws Exception {
        super.init();
        fxmlMappings.putAll(FXMLScanner.scan());
    }

    protected URL findFxmlLocation(String pathname) {
        try {
            String absolutePath = fxmlMappings.get(pathname);
            return new File(absolutePath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
