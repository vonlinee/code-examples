package io.devpl.toolkit.fxui.framework;

import io.devpl.toolkit.fxui.framework.fxml.FXMLScanner;
import io.devpl.toolkit.fxui.utils.Messages;
import javafx.application.Application;

public abstract class JavaFXApplication extends Application {

    protected ApplicationContext applicationContext;

    @Override
    public final void init() throws Exception {
        super.init();
        applicationContext = createApplicationContext();
        prepareApplicationContext(applicationContext);
        this.onInit();
    }

    private ApplicationContext createApplicationContext() {
        return ApplicationContext.getInstance();
    }

    private void prepareApplicationContext(ApplicationContext context) {
        // 扫描所有的FXML文件
        context.addFxmlMappings(FXMLScanner.scan());
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
