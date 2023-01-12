package io.devpl.toolkit.framework.mvc;

import javafx.event.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscriber;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * 所有控制器的基类，控制器是单例对象
 */
@Subscriber
public abstract class AbstractViewController implements ViewController, Initializable {

    protected final Log log = LogFactory.getLog(getClass());

    // 事件总线
    private static final EventBus bus = EventBus.builder()
                                                .logNoSubscriberMessages(true)
                                                .allowEmptySubscriber(true)
                                                .build();

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

    /**
     * 事件分派者链
     */
    private static final ViewControllerEventDispatchChain viewControllerDispatcherChain = new ViewControllerEventDispatchChain();

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return viewControllerDispatcherChain;
    }

    /**
     * 对于Controller来说只有一个事件分派者
     */
    private final ViewControllerEventDispatcher eventDispatcher;

    public AbstractViewController() {
        eventDispatcher = new ViewControllerEventDispatcher();
        viewControllerDispatcherChain.append(eventDispatcher);
    }
}
