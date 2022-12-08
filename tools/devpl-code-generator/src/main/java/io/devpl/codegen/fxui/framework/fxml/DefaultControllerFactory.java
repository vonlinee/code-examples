package io.devpl.codegen.fxui.framework.fxml;

import io.devpl.codegen.fxui.framework.ApplicationContext;
import javafx.util.Callback;

import java.util.WeakHashMap;

public class DefaultControllerFactory implements ControllerFactory {

    private final ApplicationContext applicationContext;

    public DefaultControllerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object getController(Class<?> param) {
        return applicationContext.getController(param);
    }
}
