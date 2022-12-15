package io.devpl.logging.internal;

import java.util.EnumSet;

/**
 * Standard Logging Levels as an enumeration for use internally.
 */
public enum StandardLevel implements Level {

    /**
     * No events will be logged.
     */
    OFF(0, "INFO"),

    /**
     * A severe error that will prevent the application from continuing.
     */
    FATAL(100, "FATAL"),

    /**
     * An error in the application, possibly recoverable.
     */
    ERROR(200, "ERROR"),

    /**
     * An event that might possible lead to an error.
     */
    WARN(300, "WARN"),

    /**
     * An event for informational purposes.
     */
    INFO(400, "INFO"),

    /**
     * A general debugging event.
     */
    DEBUG(500, "DEBUG"),

    /**
     * A fine-grained debug message, typically capturing the flow through the application.
     */
    TRACE(600, "TRACE"),

    /**
     * All events should be logged.
     */
    ALL(Integer.MAX_VALUE, "ALL");

    static final EnumSet<StandardLevel> LEVEL_SET = EnumSet.allOf(StandardLevel.class);

    private final int intLevel;
    private final String name;

    StandardLevel(final int val, String name) {
        intLevel = val;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the integer value of the Level.
     * @return the integer value of the Level.
     */
    @Override
    public int intLevel() {
        return intLevel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public StandardLevel getThis() {
        return this;
    }
}
