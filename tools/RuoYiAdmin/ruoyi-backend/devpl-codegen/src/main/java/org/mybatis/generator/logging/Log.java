package org.mybatis.generator.logging;

/**
 * @see org.slf4j.Logger
 */
public interface Log {

    /**
     * 日志常量
     * @see org.slf4j.event.Level
     */
    int TRACE_INT = 0;
    int DEBUG_INT = 10;
    int INFO_INT = 20;
    int WARN_INT = 30;
    int ERROR_INT = 40;

    /**
     * Case-insensitive String constant used to retrieve the name of the root logger.
     * @since 1.3
     */
    String ROOT_LOGGER_NAME = "ROOT";

    /**
     * Return the name of this <code>Logger</code> instance.
     * @return name of this logger instance
     */
    String getName();

    /**
     * Returns whether this Logger is enabled for a given {@link Level}.
     * @param level
     * @return true if enabled, false otherwise.
     */
    default boolean isEnabledForLevel(Level level) {
        int levelInt = level.toInt();
        switch (levelInt) {
            case (Log.TRACE_INT):
                return isTraceEnabled();
            case (Log.DEBUG_INT):
                return isDebugEnabled();
            case (Log.INFO_INT):
                return isInfoEnabled();
            case (Log.WARN_INT):
                return isWarnEnabled();
            case (Log.ERROR_INT):
                return isErrorEnabled();
            default:
                throw new IllegalArgumentException("Level [" + level + "] not recognized.");
        }
    }

    /**
     * Is the logger instance enabled for the TRACE level?
     * @return True if this Logger is enabled for the TRACE level,
     * false otherwise.
     * @since 1.4
     */
    boolean isTraceEnabled();

    /**
     * Log a message at the TRACE level.
     * @param msg the message string to be logged
     * @since 1.4
     */
    void trace(String msg);

    /**
     * Log a message at the TRACE level according to the specified format
     * and argument.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level.
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    void trace(String format, Object arg);

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level.
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    void trace(String format, Object arg1, Object arg2);

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the TRACE level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for TRACE. The variants taking {@link #trace(String, Object) one} and
     * {@link #trace(String, Object, Object) two} arguments exist solely in order to avoid this hidden cost.
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @since 1.4
     */
    void trace(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the TRACE level with an
     * accompanying message.
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     * @since 1.4
     */
    void trace(String msg, Throwable t);

    /**
     * Similar to {@link #isTraceEnabled()} method except that the
     * marker data is also taken into account.
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the TRACE level,
     * false otherwise.
     * @since 1.4
     */
    boolean isTraceEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the TRACE level.
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     * @since 1.4
     */
    void trace(Marker marker, String msg);

    /**
     * This method is similar to {@link #trace(String, Object)} method except that the
     * marker data is also taken into consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    void trace(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #trace(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    void trace(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #trace(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param argArray an array of arguments
     * @since 1.4
     */
    void trace(Marker marker, String format, Object... argArray);

    /**
     * This method is similar to {@link #trace(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     * @since 1.4
     */
    void trace(Marker marker, String msg, Throwable t);

    /**
     * Is the logger instance enabled for the DEBUG level?
     * @return True if this Logger is enabled for the DEBUG level,
     * false otherwise.
     */
    boolean isDebugEnabled();

    /**
     * Log a message at the DEBUG level.
     * @param msg the message string to be logged
     */
    void debug(String msg);

    /**
     * Log a message at the DEBUG level according to the specified format
     * and argument.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level.
     * @param format the format string
     * @param arg    the argument
     */
    void debug(String format, Object arg);

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level.
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void debug(String format, Object arg1, Object arg2);

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the DEBUG level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for DEBUG. The variants taking
     * {@link #debug(String, Object) one} and {@link #debug(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    void debug(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the DEBUG level with an
     * accompanying message.
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    void debug(String msg, Throwable t);

    /**
     * Similar to {@link #isDebugEnabled()} method except that the
     * marker data is also taken into account.
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the DEBUG level,
     * false otherwise.
     */
    boolean isDebugEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the DEBUG level.
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    void debug(Marker marker, String msg);

    /**
     * This method is similar to {@link #debug(String, Object)} method except that the
     * marker data is also taken into consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void debug(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #debug(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void debug(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #debug(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    void debug(Marker marker, String format, Object... arguments);

    /**
     * This method is similar to {@link #debug(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    void debug(Marker marker, String msg, Throwable t);


    void warn(String s);

    /**
     * Is the logger instance enabled for the INFO level?
     * @return True if this Logger is enabled for the INFO level,
     * false otherwise.
     */
    boolean isInfoEnabled();

    /**
     * Log a message at the INFO level.
     * @param msg the message string to be logged
     */
    void info(String msg);

    /**
     * Log a message at the INFO level according to the specified format
     * and argument.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level.
     * @param format the format string
     * @param arg    the argument
     */
    void info(String format, Object arg);

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level.
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void info(String format, Object arg1, Object arg2);

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for INFO. The variants taking
     * {@link #info(String, Object) one} and {@link #info(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    void info(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    void info(String msg, Throwable t);


    /**
     * Is the logger instance enabled for the WARN level?
     * @return True if this Logger is enabled for the WARN level,
     * false otherwise.
     */
    boolean isWarnEnabled();

    /**
     * Log a message at the WARN level according to the specified format
     * and argument.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the WARN level.
     * @param format the format string
     * @param arg    the argument
     */
    void warn(String format, Object arg);

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the WARN level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for WARN. The variants taking
     * {@link #warn(String, Object) one} and {@link #warn(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    void warn(String format, Object... arguments);

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the WARN level.
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void warn(String format, Object arg1, Object arg2);

    /**
     * Log an exception (throwable) at the WARN level with an
     * accompanying message.
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    void warn(String msg, Throwable t);

    /**
     * Similar to {@link #isWarnEnabled()} method except that the marker
     * data is also taken into consideration.
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the WARN level,
     * false otherwise.
     */
    boolean isWarnEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the WARN level.
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    void warn(Marker marker, String msg);

    /**
     * This method is similar to {@link #warn(String, Object)} method except that the
     * marker data is also taken into consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void warn(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #warn(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void warn(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #warn(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    void warn(Marker marker, String format, Object... arguments);

    /**
     * This method is similar to {@link #warn(String, Throwable)} method
     * except that the marker data is also taken into consideration.
     * @param marker the marker data for this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    void warn(Marker marker, String msg, Throwable t);

    /**
     * Is the logger instance enabled for the ERROR level?
     * @return True if this Logger is enabled for the ERROR level,
     * false otherwise.
     */
    boolean isErrorEnabled();

    /**
     * Similar to {@link #isErrorEnabled()} method except that the
     * marker data is also taken into consideration.
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the ERROR level,
     * false otherwise.
     */
    boolean isErrorEnabled(Marker marker);


    /**
     * Log a message at the ERROR level.
     * @param msg the message string to be logged
     */
    void error(String msg);


    /**
     * Log a message at the ERROR level according to the specified format
     * and argument.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level.
     * @param format the format string
     * @param arg    the argument
     */
    void error(String format, Object arg);

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level.
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void error(String format, Object arg1, Object arg2);

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     *
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the ERROR level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for ERROR. The variants taking
     * {@link #error(String, Object) one} and {@link #error(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    void error(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    void error(String msg, Throwable t);


    /**
     * Log a message with the specific Marker at the ERROR level.
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    void error(Marker marker, String msg);

    /**
     * This method is similar to {@link #error(String, Object)} method except that the
     * marker data is also taken into consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void error(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #error(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void error(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #error(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    void error(Marker marker, String format, Object... arguments);

    /**
     * This method is similar to {@link #error(String, Throwable)}
     * method except that the marker data is also taken into
     * consideration.
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    void error(Marker marker, String msg, Throwable t);
}