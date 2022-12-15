package io.devpl.logging.internal;

import io.devpl.logging.Log;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The log level that {@link Log} can log at.
 */
public interface Level extends Serializable {

    ConcurrentMap<String, Level> LEVELS = new ConcurrentHashMap<>(); // SUPPRESS CHECKSTYLE

    Level INFO = StandardLevel.INFO;
    Level ERROR = StandardLevel.ERROR;
    Level DEBUG = StandardLevel.DEBUG;
    Level FATAL = StandardLevel.FATAL;
    Level ALL = StandardLevel.ALL;
    Level TRACE = StandardLevel.TRACE;
    Level WARN = StandardLevel.WARN;

    String getName();

    int intLevel();

    <T extends Level> T getThis();

    /**
     * 等同于
     * @param other subclass of Level
     * @return int
     * @see Comparable#compareTo(Object)
     */
    default int compareTo(final Level other) {
        if (other == null) {
            return 1;
        }
        return Integer.compare(intLevel(), other.intLevel());
    }

    /**
     * Method to convert custom Levels into a StandardLevel for conversion to other systems.
     * @param intLevel The integer value of the Level.
     * @return The StandardLevel.
     */
    static Level getStandardLevel(final int intLevel) {
        StandardLevel level = StandardLevel.OFF;
        for (final StandardLevel lvl : StandardLevel.LEVEL_SET) {
            if (lvl.intLevel() > intLevel) {
                break;
            }
            level = lvl;
        }
        return level;
    }
}