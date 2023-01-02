package de.saxsys.mvvmfx.fxml;

import io.devpl.toolkit.fxui.utils.ClassUtils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FxmlViewControllerFactory implements FxmlControllerFactory {

    private String cuurentLoadingFxmlId;
    private final Map<String, Reference<Object>> controllerCacheMap;

    public FxmlViewControllerFactory() {
        this.controllerCacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public Object getController(Class<?> param) {
        final String fxmlId = cuurentLoadingFxmlId;
        final Map<String, Reference<Object>> cache = this.controllerCacheMap;
        Object instance;
        if (cache.containsKey(fxmlId)) {
            final Reference<Object> controllerCache = cache.get(fxmlId);
            instance = controllerCache.get();
            if (controllerCache.get() == null) {
                instance = ClassUtils.instantiate(param);
                cache.put(fxmlId, new WeakReference<>(instance));
            }
        } else {
            instance = ClassUtils.instantiate(param);
            cache.put(fxmlId, new WeakReference<>(instance));
        }
        return instance;
    }

    @Override
    public void setCurrentLoadingFxmlId(String fxmlId) {
        this.cuurentLoadingFxmlId = fxmlId;
    }
}
