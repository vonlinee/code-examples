package io.devpl.logging.format;

public abstract class LogTemplate {

    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Implement this method to create your own layout format.
     */
    public abstract String format(LogMessage message);
}
