package io.devpl.codegen.fxui.framework.fxml;

import io.devpl.codegen.fxui.framework.ApplicationContext;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultControllerFactory implements ControllerFactory {

    private final ApplicationContext applicationContext;

    public DefaultControllerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private final Map<Class<?>, WeakReference<Object>> controllerCache = new ConcurrentHashMap<>();

    @Override
    public Object getController(Class<?> param) {
        if (!controllerCache.containsKey(param)) {
            try {
                Constructor<?> constructor = param.getConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("实例化" + param + "异常");
            }
        }
        WeakReference<Object> controllerRef = controllerCache.get(param);
        if (controllerRef.get() == null) {
            // 不为null
            controllerRef = new WeakReference<>(applicationContext.getController(param));
            controllerCache.put(param, controllerRef);
        }
        return controllerRef.get();
    }

    @Override
    public boolean contains(Class<?> controllerClass) {
        return controllerCache.containsKey(controllerClass);
    }
}
