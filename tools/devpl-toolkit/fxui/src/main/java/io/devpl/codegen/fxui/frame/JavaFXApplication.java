package io.devpl.codegen.fxui.frame;

import io.devpl.codegen.fxui.utils.FXMLScanner;
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

    public static void run(Class<? extends Application> appClass, String[] args) {
        Application.launch(appClass, args);
    }
}
