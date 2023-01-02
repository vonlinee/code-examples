package de.saxsys.mvvmfx.core;

import de.saxsys.mvvmfx.fxml.FxmlViewControllerFactory;
import de.saxsys.mvvmfx.utils.LRUCache;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * <p>
 * The typical usage will look like this:
 * <br>
 * <pre>
 * public class MyCoolViewModel implements ViewModel {...}
 * public class MyCoolView implements FxmlView{@code <MyCoolViewModel>} {...}
 *
 *
 * public class MyApp extends Application {
 *
 *    {@literal @}Override
 *     public void start(Stage stage) {
 * 		ViewTuple{@code <MyCoolView, MyCoolViewModel>} viewTuple
 * 				= ViewLoader.fxmlView(MyCoolView.class).load();
 *
 * 		Parent root = viewTuple.getView();
 * 		stage.setScene(new Scene(root));
 * 		stage.show();
 *     }
 * }
 *
 * </pre>
 * <p>
 * <p>
 * This class is implemented as a Step-Builder. You can choose between
 * {@link FxmlView} and {@link NodeView} with the first method call. After that
 * you will only get builder-methods that are suitable for the view type you
 * have chosen.
 * @author manuel.mauky
 */
public class ViewLoader {

    private final LRUCache<Class<?>, ViewComponent> viewCache = new LRUCache<>(100);

    static final FxmlViewControllerFactory viewControllerFactory = new FxmlViewControllerFactory();

    /**
     * 单例
     */
    private static final ViewLoader loader = new ViewLoader();

    public static ViewComponent loadByClass(Class<?> viewClass) {
        ViewComponent vc = loader.load(viewClass);
        vc.onCreate();
        return vc;
    }

    public ViewComponent load(Class<?> viewClass) {
        ViewComponent viewComponent = viewCache.get(viewClass);
        if (viewComponent != null) {
            return viewComponent;
        }
        viewCache.put(viewClass, viewComponent = reloadView(viewClass));
        return viewComponent;
    }

    @SuppressWarnings("unchecked")
    private ViewComponent reloadView(Class<?> viewClass) {
        // 判断是FXML还是基于Java代码
        if (FxmlView.class.isAssignableFrom(viewClass)) {
            return loadFxmlView((Class<FxmlView<?>>) viewClass);
        } else if (NodeView.class.isAssignableFrom(viewClass)) {
            throw new RuntimeException(viewClass.getName());
        }
        return null;
    }

    public FxmlViewComponent loadFxmlView(Class<FxmlView<?>> viewClass) {
        FxmlViewComponent viewElement = null;
        FxmlLocation fxmlLocationAnnotation = viewClass.getAnnotation(FxmlLocation.class);
        String location;
        if (fxmlLocationAnnotation == null) {
            String packageName = viewClass.getPackageName();
            location = packageName.replace(".", "/") + "/" + viewClass.getSimpleName() + ".fxml";
        } else {
            location = fxmlLocationAnnotation.location();
        }
        URL resource = Thread.currentThread().getContextClassLoader().getResource(location);
        if (resource == null) {
            throw new RuntimeException("fxml [" + location + "] does not exists!");
        }
        viewElement = new FxmlViewComponent(location, resource, null, viewControllerFactory);
        return viewElement;
    }
}
