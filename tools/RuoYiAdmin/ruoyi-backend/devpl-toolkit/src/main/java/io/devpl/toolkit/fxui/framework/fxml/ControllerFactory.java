package io.devpl.toolkit.fxui.framework.fxml;

import javafx.util.Callback;

/**
 * 控制器工厂类
 */
public interface ControllerFactory extends Callback<Class<?>, Object> {

    @Override
    default Object call(Class<?> param) {
        return getController(param);
    }

    Object getController(Class<?> param);
}
