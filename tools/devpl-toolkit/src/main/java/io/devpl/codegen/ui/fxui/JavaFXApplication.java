package io.devpl.codegen.ui.fxui;

import javafx.application.Application;

import java.util.HashMap;
import java.util.Map;

public abstract class JavaFXApplication extends Application {

    protected final Map<String, String> fxmlMappings = new HashMap<>();

    public JavaFXApplication() {
        fxmlMappings.putAll(FXMLScanner.scan());
    }

    @Override
    public void init() throws Exception {

    }
}
