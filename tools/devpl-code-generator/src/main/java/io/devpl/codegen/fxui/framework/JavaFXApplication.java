package io.devpl.codegen.fxui.framework;

import javafx.application.Application;

public abstract class JavaFXApplication extends Application {

    private final ApplicationContext context = new ApplicationContext();

    @Override
    public void init() throws Exception {
        super.init();
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}
