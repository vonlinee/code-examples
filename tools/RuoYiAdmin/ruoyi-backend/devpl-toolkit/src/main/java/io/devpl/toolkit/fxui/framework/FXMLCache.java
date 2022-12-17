package io.devpl.toolkit.fxui.framework;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 缓存FXML文件相关的内容，FXMLLoader，根节点，Controller实例
 */
public final class FXMLCache implements Callback<Class<?>, Object> {

    private final URL location;
    private WeakReference<FXMLLoader> fxmlLoaderCache;

    public FXMLCache(URL location) {
        this.location = Objects.requireNonNull(location, "location of fxml cannot be null!");
    }

    /**
     * 不支持嵌套Controller
     * 获取FXML对应的FXMLLoader实例
     * @return FXMLLoader实例
     */
    public FXMLLoader getFXMLLoader() {
        FXMLLoader loader;
        if (fxmlLoaderCache == null || fxmlLoaderCache.get() == null) {
            loader = new FXMLLoader();
            fxmlLoaderCache = new WeakReference<>(loader);
            loader.setLocation(this.location);
            loader.setControllerFactory(this);
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            loader = fxmlLoaderCache.get();
        }
        return loader;
    }

    /**
     * 在getFXMLLoader里调用
     * @param param The single argument upon which the returned value should be
     *              determined.
     * @return Controller实例
     */
    @Override
    public Object call(Class<?> param) {
        final FXMLLoader loader = fxmlLoaderCache.get();
        if (loader == null || loader.getController() == null) {
            return newControllerInstance(param);
        }
        return loader.getController();
    }

    /**
     * 通过默认构造器实例化一个类
     * @param clazz 类
     * @return 对象实例
     * @throws RuntimeException 实例化失败
     */
    private Object newControllerInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("cannot instantiate controller[" + clazz + "]", e);
        }
    }
}
