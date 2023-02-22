package io.devpl.fxtras.mvc;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 所有控制器的基类，控制器是单例对象
 */
public abstract class FxmlView extends ViewBase implements View, Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
