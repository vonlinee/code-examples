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

    @Inject
    protected FxmlViewComponent fxmlViewComponent;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        onInit(fxmlViewComponent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public R getRoot() {
        return (R) fxmlViewComponent.getRoot();
    }

    /**
     * 初始化方法
     */
    protected void onInit(ViewComponent viewComponent) {

    }
}
