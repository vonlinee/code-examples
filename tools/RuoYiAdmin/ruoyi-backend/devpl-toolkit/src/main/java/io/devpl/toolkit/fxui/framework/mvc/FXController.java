package io.devpl.toolkit.fxui.framework.mvc;

import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import org.greenrobot.eventbus.EventBus;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 所有控制器的基类，控制器是单例对象
 */
public abstract class FXController implements EventTarget, Initializable {

    protected final Log log = LogFactory.getLog(getClass());

    // Android的事件总线
    private static final EventBus bus = new EventBus();

    /**
     * 缓存单例控制器
     * 并不是所有的都会在一开始初始化
     */
    private static final Map<Class<? extends FXController>, FXController> controllers = new ConcurrentHashMap<>();

    public FXController() {
        // This逃逸问题？
        controllers.put(this.getClass(), this);
    }

    /**
     * 将自身注册进事件总线
     * 如果没有@Subscribe修饰的方法，那么会报错
     */
    public final void registerThis() {
        bus.register(this);
    }

    public final void post(Object event) {
        bus.post(event);
    }

    public final void unregister() {
        bus.unregister(this);
    }

    public final boolean hasSubscriberForEvent(Class<?> eventClass) {
        if (eventClass == null) return false;
        return bus.hasSubscriberForEvent(eventClass);
    }

    @SuppressWarnings("unchecked")
    public <T extends FXController> T getController(Class<T> controllerType) {
        return (T) controllers.get(controllerType);
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail;
    }

    @SuppressWarnings("unchecked")
    protected final <T> T getUserData(Node node) {
        return (T) node.getUserData();
    }
}
