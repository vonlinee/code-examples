package io.devpl.logging;

public class LogUtils {

    public static void report(String msg, Throwable t) {
        System.err.println(msg);
        System.err.println("Reported exception:");
        t.printStackTrace();
    }

    protected static boolean debugEnabled = true;

    private static final String PREFIX = "log4j: ";

    /**
     * In quietMode not even errors generate any output.
     */
    private static boolean quietMode = false;

    /**
     * This method is used to output log4j internal debug statements. Output goes to
     * <code>System.out</code>.
     */
    public static void debug(String msg, Throwable t) {
        if (debugEnabled && !quietMode) {
            System.out.println(PREFIX + msg);
            if (t != null) {
                t.printStackTrace(System.out);
            }
        }
    }
}
