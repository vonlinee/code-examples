package io.devpl.codegen.fxui.framework;

import io.devpl.codegen.fxui.framework.fxml.FXMLScanner;
import io.devpl.codegen.fxui.utils.Messages;
import javafx.application.Application;

public abstract class JavaFXApplication extends Application {

    @Override
    public final void init() throws Exception {
        super.init();
        Messages.init();
        final ApplicationContext applicationContext = createApplicationContext();
        prepareApplicationContext(applicationContext);
        this.onInit();
    }

    private ApplicationContext createApplicationContext() {
        return ApplicationContext.getInstance();
    }

    private void prepareApplicationContext(ApplicationContext context) {
        context.addFxmlMappings(FXMLScanner.scan());
    }

    protected void onInit() throws Exception {
    }

    protected void onStop() {

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        onStop();
    }
}
