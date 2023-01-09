package io.devpl.toolkit.framework.mvc;

import io.devpl.toolkit.framework.utils.WeakValueHashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class ViewLoader {

    private static final WeakValueHashMap<Class<?>, ViewLoader> loaderCache = new WeakValueHashMap<>();

    private final Parent rootNode;
    private final Object viewController;

    public ViewLoader(Parent rootNode, Object viewController) {
        this.rootNode = rootNode;
        this.viewController = viewController;
    }

    public static ViewLoader load(Class<?> clazz) {
        if (!ViewController.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("cannot load class ViewController");
        }
        ViewLoader loader = loaderCache.get(clazz);
        if (loader == null) {
            // 重新加载
            final FxmlView fxmlViewAnnotation = clazz.getAnnotation(FxmlView.class);
            if (fxmlViewAnnotation != null) {
                String fxmlLocation = fxmlViewAnnotation.location();
                if (fxmlLocation.isEmpty()) {
                    String packageName = clazz.getPackageName();
                    fxmlLocation = packageName.replace(".", "/") + "/" + clazz.getSimpleName() + ".fxml";
                }
                URL resource = Thread.currentThread().getContextClassLoader().getResource(fxmlLocation);
                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                try {
                    loaderCache.put(clazz, loader = new ViewLoader(fxmlLoader.load(), fxmlLoader.getController()));
                } catch (IOException e) {
                    throw new RuntimeException("failed to load fxml [" + fxmlLocation + "]", e);
                }
            } else {
                throw new RuntimeException("暂不支持非FXML类型的视图加载");
            }
        }
        return loader;
    }

    @SuppressWarnings("unchecked")
    public <T extends Parent> T getRoot() {
        return (T) rootNode;
    }

    @SuppressWarnings("unchecked")
    public <T extends ViewController> T getViewController() {
        return (T) viewController;
    }
}
