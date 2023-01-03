package de.saxsys.mvvmfx.core;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javax.inject.Inject;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * View包含UI及原来的Controller层
 * @param <R>
 */
public abstract class AbstractFxmlView<R extends Parent> implements FxmlView<R>, Initializable {

    @Override
    public final void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public R getRoot() {
        return null;
    }
}
