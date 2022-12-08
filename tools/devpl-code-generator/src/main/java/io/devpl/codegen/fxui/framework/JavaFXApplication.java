package io.devpl.codegen.fxui.framework;

import io.devpl.codegen.fxui.framework.fxml.FXMLScanner;
import javafx.application.Application;

public abstract class JavaFXApplication extends Application {

    private final ApplicationContext context = new ApplicationContext();

    @Override
    public void init() throws Exception {
        super.init();
        context.addFxmlMappings(FXMLScanner.scan());
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}
