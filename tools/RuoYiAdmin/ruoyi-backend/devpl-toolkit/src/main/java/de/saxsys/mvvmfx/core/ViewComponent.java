package de.saxsys.mvvmfx.core;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * <p>
 * Tuple for carrying view / code-behind pair. The code-behind part is the class which is known as the controller class
 * behind a FXML file.
 * </p>
 *
 * <p>
 * As a user you typically won't create instances of this class on your own. Instead you will obtain instances of this
 * class with the {@link ViewLoader} when you are loading a view.
 * </p>
 * 视图组件
 * <p>
 * Instances of this class are immutable.
 * </p>
 * the generic type of the view that was loaded.
 * the generic type of the viewModel that was loaded.
 */
public abstract class ViewComponent extends Parent {

    Reference<? extends View> viewRef;
    Reference<Parent> viewRootRef;

    /**
     * <p>
     * The code behind part of the view. When using FXML ({@link FxmlView}) this will be an instance of the class that
     * is specified in the fxml file with <code>fx:controller</code>.
     * </p>
     * <p>
     * When the view is implemented in pure java ({@link NodeView})
     * </p>
     * @return the code behind of the View.
     */
    public final View getView() {
        View view;
        if (viewRef == null) {
            viewRef = new WeakReference<>(view = createView());
        } else {
            view = viewRef.get();
        }
        if (view == null) {
            viewRef = new WeakReference<>(view = createView());
        }
        return view;
    }

    /**
     * 如果是加载FXML，每次加载FXML，View和Root节点两者都要更新
     * @param <T> 类型
     * @return View实例
     */
    abstract <T> View createView();

    abstract Parent createRoot();

    /**
     * The root object of the view. This can be added to the scene graph.
     * @return the view
     */
    public final Parent getRoot() {
        Parent viewRoot;
        if (viewRootRef == null) {
            viewRootRef = new WeakReference<>(viewRoot = createRoot());
        } else {
            viewRoot = viewRootRef.get();
        }
        if (viewRoot == null) {
            viewRootRef = new WeakReference<>(viewRoot = createRoot());
        }
        return viewRoot;
    }

    public Node findNodeById(String id) {
        return lookup("#" + id);
    }

    protected void onCreate() {

    }
}
