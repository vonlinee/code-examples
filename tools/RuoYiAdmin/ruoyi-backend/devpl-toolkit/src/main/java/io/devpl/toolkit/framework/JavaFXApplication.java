package io.devpl.toolkit.framework;

import javafx.application.Application;

public abstract class JavaFXApplication extends Application {

    @Override
    public final void init() throws Exception {
        super.init();
        this.onInit();
    }

    protected void onInit() throws Exception {
    }

    protected void onStop() {

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        this.onStop();
    }
}
