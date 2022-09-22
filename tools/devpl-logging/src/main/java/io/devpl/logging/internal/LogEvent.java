package io.devpl.logging.internal;

import java.util.EventObject;

/**
 * <p>
 * 事件源即为Logger对象，监听事件的为Appender
 * </p>
 * <p>
 * 封装要记录的所有信息
 */
public abstract class LogEvent extends EventObject {

    protected int threadPriority;
    protected long threadId;
    protected long nanoTime;
    protected boolean includeLocation;
    protected boolean endOfBatch = false;
    protected Level level;
    protected String threadName;
    protected String loggerName;
    protected Message message;
    protected String messageFormat;
    protected StringBuilder messageText;
    protected Object[] parameters;
    protected Throwable thrown;
    protected Marker marker;
    protected String loggerFqcn;
    protected StackTraceElement source;
    transient boolean reserved = false;

    /**
     * Constructs a prototypical Event.
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public LogEvent(Object source) {
        super(source);
    }
}
