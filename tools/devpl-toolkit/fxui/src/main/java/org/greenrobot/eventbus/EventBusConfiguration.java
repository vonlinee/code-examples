package org.greenrobot.eventbus;

/**
 * 事件总线配置
 */
public class EventBusConfiguration {
    private boolean throwSubscriberException;
    private boolean logSubscriberExceptions;
    private boolean logNoSubscriberMessages;
    private boolean sendSubscriberExceptionEvent;
    private boolean sendNoSubscriberEvent;
    private boolean eventInheritance;
}
