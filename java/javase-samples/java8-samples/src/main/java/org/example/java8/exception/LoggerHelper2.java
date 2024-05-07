package org.example.java8.exception;

public class LoggerHelper2 {

    private static final String SEPARATOR = "\r\n";
    private static final String CAUSE_CAPTION = "Caused by: ";
    private static final String SUPPRESSED_CAPTION = "Suppressed: ";

    /**
     * 默认返回前10行异常栈信息
     */
    public static String printTop10StackTrace(Throwable e) {
        if (e == null) {
            return "";
        }
        return printStackTrace(e, 20);
    }

    public static String printStackTrace(Throwable e, int maxLineCount) {
        if (e == null || maxLineCount <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(maxLineCount * 10);
        sb.append(e).append(SEPARATOR);
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null) {
            return e.toString();
        }
        int count = Math.min(maxLineCount, trace.length);
        int framesInCommon = trace.length - count;
        for (int i = 0; i < count; i++) {
            sb.append("\tat ").append(trace[i]).append(SEPARATOR);
        }
        if (framesInCommon != 0) {
            sb.append("\t... ").append(framesInCommon).append(" more").append(SEPARATOR);
        }
        // Print suppressed exceptions, if any
        Throwable[] suppressedExceptions = e.getSuppressed();
        if (suppressedExceptions != null) {
            for (Throwable suppressedException : suppressedExceptions) {
                sb.append(printEnclosedStackTrace(suppressedException, maxLineCount, trace, SUPPRESSED_CAPTION, "\t"));
            }
        }
        // Print cause, if any
        Throwable cause = e.getCause();
        if (cause != null) {
            sb.append(printEnclosedStackTrace(cause, maxLineCount, trace, CAUSE_CAPTION, ""));
        }
        return sb.toString();
    }

    private static String printEnclosedStackTrace(Throwable e, int maxLineCount, StackTraceElement[] enclosingTrace,
                                                  String caption, String prefix) {
        StringBuilder sb = new StringBuilder(maxLineCount * 5);
        StackTraceElement[] trace = e.getStackTrace();
        int m = trace.length - 1;
        int n = enclosingTrace.length - 1;
        while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n])) {
            m--;
            n--;
        }
        int count = Math.min(maxLineCount, (m + 1));
        int framesInCommon = trace.length - count;
        // Print our stack trace
        sb.append(prefix).append(caption).append(e).append(SEPARATOR);
        for (int i = 0; i < count; i++) {
            sb.append(prefix).append("\tat ").append(trace[i]).append(SEPARATOR);
        }
        if (framesInCommon != 0) {
            sb.append(prefix).append("\t... ").append(framesInCommon).append(" more").append(SEPARATOR);
        }
        // Print suppressed exceptions, if any
        Throwable[] suppressedExceptions = e.getSuppressed();
        if (suppressedExceptions != null) {
            for (Throwable suppressedException : suppressedExceptions) {
                sb.append(printEnclosedStackTrace(suppressedException, maxLineCount, trace, SUPPRESSED_CAPTION, prefix + "\t"));
            }
        }
        // Print cause, if any
        Throwable cause = e.getCause();
        if (cause != null) {
            sb.append(printEnclosedStackTrace(cause, maxLineCount, trace, CAUSE_CAPTION, prefix));
        }
        return sb.toString();
    }
}