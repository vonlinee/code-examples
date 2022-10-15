package io.devpl.eventbus;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Subscribe {

    /**
     * 线程模型
     *
     * @return
     */
    ThreadMode threadMode() default ThreadMode.POSTING;

    /**
     * 粘性事件
     * If true, delivers the most recent sticky event (posted with
     * {@link DefaultEventBus#postSticky(Object)}) to this subscriber (if event available).
     */
    boolean sticky() default false;

    /**
     * 订阅优先级
     * Subscriber priority to influence the order of event delivery.
     * Within the same delivery thread ({@link ThreadMode}), higher priority subscribers will receive events before
     * others with a lower priority. The default priority is 0. Note: the priority does *NOT* affect the order of
     * delivery among subscribers with different {@link ThreadMode}s!
     */
    int priority() default 0;
}

