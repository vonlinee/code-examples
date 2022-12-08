package io.devpl.codegen.fxui.framework.fxml;

import javafx.util.Callback;

public interface ControllerFactory extends Callback<Class<?>, Object> {

    @Override
    default Object call(Class<?> param) {
        return getController(param);
    }

    Object getController(Class<?> param);
}
