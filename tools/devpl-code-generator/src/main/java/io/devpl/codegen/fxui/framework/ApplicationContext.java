package io.devpl.codegen.fxui.framework;

import io.devpl.codegen.fxui.framework.fxml.ControllerFactory;
import io.devpl.codegen.fxui.framework.fxml.DefaultControllerFactory;
import io.devpl.codegen.fxui.framework.fxml.FXMLCache;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用上下文
 *
 *
 * Scene loads too slow
 * https://stackoverflow.com/questions/22328087/scene-loads-too-slow
 * 提升性能
 * https://stackoverflow.com/questions/11734885/javafx2-very-poor-performance-when-adding-custom-made-fxmlpanels-to-gridpane
 */
public class ApplicationContext {

    // Instance of StackWalker used to get caller class (must be private)
    private static final StackWalker walker = AccessController.doPrivileged((PrivilegedAction<StackWalker>) () -> StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE));
    // Indicates permission to get the ClassLoader
    private static final RuntimePermission GET_CLASSLOADER_PERMISSION = new RuntimePermission("getClassLoader");

    /**
     * 缓存所有的FXML位置信息
     */
    private final Map<String, FXMLCache> fxmlCacheMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> controllerCacheMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, FXMLCache> controllerFxmlRelationMap = new ConcurrentHashMap<>();

    static class ApplicationContextHolder {
        static ApplicationContext context = new ApplicationContext();
    }

    public void addFxmlMappings(Map<String, String> fxmlMappings) {
        fxmlMappings.forEach((fxmlKey, fxmlUrl) -> {
            final FXMLCache fxmlCache = new FXMLCache(fxmlUrl);
            this.fxmlCacheMap.put(fxmlKey, fxmlCache);
            final Object controller = fxmlCache.getController();
        });
    }

    private ControllerFactory controllerFactory = new DefaultControllerFactory(this);

    /**
     * Sets the controller factory used by this loader.
     * @param controllerFactory the controller factory
     * @since JavaFX 2.1
     */
    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    /**
     * 获取控制器
     * @param controllerType
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getController(Class<T> controllerType) {
        Object controller = controllerCacheMap.get(controllerType);
        if (controller == null) {
            final FXMLCache fxmlCache = controllerFxmlRelationMap.get(controllerType);
            controller = fxmlCache.getController();
        }
        return (T) controller;
    }

    private ClassLoader classLoader = null;

    /**
     * Sets the classloader used by this loader and clears any existing
     * imports.
     * @param classLoader the classloader
     * @since JavaFX 2.1
     */
    public void setClassLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            throw new IllegalArgumentException();
        }
        this.classLoader = classLoader;
    }

    /**
     * Returns the classloader used by this loader.
     * @return the classloader
     * @since JavaFX 2.1
     */
    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            final SecurityManager sm = System.getSecurityManager();
            final Class<?> caller = (sm != null) ? walker.getCallerClass() : null;
            return getDefaultClassLoader(caller);
        }
        return classLoader;
    }

    private static ClassLoader defaultClassLoader = null;

    private static ClassLoader getDefaultClassLoader(Class<?> caller) {
        if (defaultClassLoader == null) {
            final SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                if (needsClassLoaderPermissionCheck(caller)) {
                    sm.checkPermission(GET_CLASSLOADER_PERMISSION);
                }
            }
            return Thread.currentThread().getContextClassLoader();
        }
        return defaultClassLoader;
    }

    /**
     * 是否检查权限
     * @param caller 调用者所在类
     * @return 是否需要检查权限
     */
    private static boolean needsClassLoaderPermissionCheck(Class<?> caller) {
        if (caller == null) {
            return false;
        }
        return !ApplicationContext.class.getModule().equals(caller.getModule());
    }
}
