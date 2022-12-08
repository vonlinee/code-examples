package io.devpl.codegen.fxui.framework.mvc;

import io.devpl.codegen.fxui.framework.JavaFXApplication;
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
     */
    private static final Map<Class<? extends FXController>, FXController>
            controllers = new ConcurrentHashMap<>();

    public FXController() {
        controllers.put(this.getClass(), this);
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
    protected  <T> T getUserData(Node node) {
        return (T) node.getUserData();
    }
}
