package io.devpl.log;

/**
 * @see org.slf4j.event.Level
 */
public enum Level {

    ERROR(Log.ERROR_INT, "ERROR"), WARN(Log.WARN_INT, "WARN"),
    INFO(Log.INFO_INT, "INFO"), DEBUG(Log.DEBUG_INT, "DEBUG"),
    TRACE(Log.TRACE_INT, "TRACE");

    public static final String NA_SUBST = "NA/SubstituteLogger";

    private final int levelInt;
    private final String levelStr;

    Level(int i, String s) {
        levelInt = i;
        levelStr = s;
    }

    public int toInt() {
        return levelInt;
    }

    public static Level intToLevel(int levelInt) {
        switch (levelInt) {
            case (10):
                return TRACE;
            case (20):
                return DEBUG;
            case (30):
                return INFO;
            case (40):
                return WARN;
            case (50):
                return ERROR;
            default:
                throw new IllegalArgumentException("Level integer [" + levelInt + "] not recognized.");
        }
    }

    /**
     * Returns the string representation of this Level.
     */
    public String toString() {
        return levelStr;
    }
}