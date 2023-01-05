package io.devpl.toolkit.framework.mvc;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.greenrobot.eventbus.EventBus;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * 所有控制器的基类，控制器是单例对象
 */
public abstract class FXController implements ViewController {

    protected final Log log = LogFactory.getLog(getClass());

    // Android的事件总线
    private static final EventBus bus = new EventBus();

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
    protected final <T> T getUserData(Node node) {
        return (T) node.getUserData();
    }

    /**
     * 从事件获取当前事件源所在的舞台对象
     * When accessing a Stage, timing is important, as the Stage is not created
     * until the very end of a View-creation process.
     * <a href="https://edencoding.com/stage-controller/">...</a>
     * @param event JavaFX event
     * @return 当前事件源所在的舞台对象
     * @throws RuntimeException 如果事件源不是Node
     */
    protected Stage getStage(Event event) {
        final Object source = event.getSource();
        if (source instanceof Node) {
            final Node node = (Node) source;
            return getStage(node);
        }
        throw new RuntimeException("event source is [" + source.getClass() + "] instead of a [Node]");
    }

    public Stage getStage(Node node) {
        final Scene scene = node.getScene();
        if (scene == null) {
            throw new RuntimeException("node [" + node + "] has not been bind to a scene!");
        }
        final Window window = scene.getWindow();
        if (window instanceof Stage) {
            return (Stage) window;
        }
        throw new RuntimeException("the window [" + window + "] is not a stage");
    }
}
