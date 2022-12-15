package io.devpl.logging;

import io.devpl.logging.internal.LogOutput;

/**
 * @see io.netty.util.internal.logging.InternalLoggerFactory
 */
public abstract class LogFactory {

    /**
     * Creates a new logger instance with the specified name.
     */
    protected abstract Log getLog(String name, LogOutput target);
}
