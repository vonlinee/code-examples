package io.devpl.toolkit.fxui.framework;

import io.devpl.toolkit.fxui.framework.fxml.ControllerFactory;
import io.devpl.toolkit.fxui.framework.fxml.FXMLCache;
import io.devpl.toolkit.fxui.utils.ClassUtils;
import javafx.fxml.FXMLLoader;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用上下文
 * Scene loads too slow
 * <a href="https://stackoverflow.com/questions/22328087/scene-loads-too-slow">...</a>
 * 提升性能
 * <a href="https://stackoverflow.com/questions/11734885/javafx2-very-poor-performance-when-adding-custom-made-fxmlpanels-to-gridpane">...</a>
 */
public final class ApplicationContext implements ControllerFactory {

    private final Log log = LogFactory.getLog(ApplicationContext.class);

    // Instance of StackWalker used to get caller class (must be private)
    private static final StackWalker walker = AccessController.doPrivileged((PrivilegedAction<StackWalker>) () -> StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE));
    // Indicates permission to get the ClassLoader
    private static final RuntimePermission GET_CLASSLOADER_PERMISSION = new RuntimePermission("getClassLoader");

    private final Map<Class<?>, String> controllerFXMLMapping = new ConcurrentHashMap<>();

    // TODO 待完善
    @Override
    public Object getController(Class<?> param) {
        final String fxmlKey = controllerFXMLMapping.get(param);
        if (fxmlKey == null) {
            // 首次加载
            return ClassUtils.instantiate(param);
        }
        final FXMLCache holder = fxmlCacheMap.get(fxmlKey);
        return holder.getController();
    }

    /**
     * 单例
     */
    private static class Holder {
        static final ApplicationContext instance = new ApplicationContext();
    }

    static ApplicationContext getInstance() {
        return Holder.instance;
    }

    /**
     * 缓存所有的FXML位置信息
     */
    private final Map<String, FXMLCache> fxmlCacheMap = new ConcurrentHashMap<>();

    public void addFxmlMappings(Map<String, String> fxmlMappings) {
        if (fxmlMappings == null || fxmlMappings.isEmpty()) {
            return;
        }
        fxmlMappings.forEach((fxmlKey, fxmlUrl) -> {
            try {
                final FXMLCache fxmlCache = new FXMLCache(new URL(fxmlUrl));
                fxmlCache.setControllerFactory(this);
                this.fxmlCacheMap.put(fxmlKey, fxmlCache);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 加载FXML，可能从缓存中加载
     * @param fxmlKey FXML相对路径
     * @return 缓存的FXMLLoader实例
     */
    public FXMLCache getFXMLCache(String fxmlKey) {
        return fxmlCacheMap.get(fxmlKey);
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
