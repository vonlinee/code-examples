package io.fxtras.fxml;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

/**
 * JavaFX 控制器工厂
 * 可用于FXMLLoader的控制器工厂
 * @see FXMLLoader#setControllerFactory(Callback)
 */
public interface ControllerFactory extends Callback<Class<?>, Object> {

    @Override
    default Object call(Class<?> param) {
        return getController(param);
    }

    Object getController(Class<?> param);
}
