package io.devpl.web.common;

import java.io.PrintStream;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class Throwables {

    private static final PrintStream stream = System.err;

    /**
     * Caption for labeling suppressed exception stack traces
     */
    private static final String SUPPRESSED_CAPTION = "Suppressed: ";
    /**
     * Caption  for labeling causative exception stack traces
     */
    private static final String CAUSE_CAPTION = "Caused by: ";

    private static final Object lock = new Object();

    public static void printStackStrace(Throwable throwable, String ignorePattern) {
        if (throwable == null) {
            throw new NullPointerException();
        }
        // Guard against malicious overrides of Throwable.equals by
        // using a Set with identity equality semantics.
        Set<Throwable> dejaVu = Collections.newSetFromMap(new IdentityHashMap<>());
        dejaVu.add(throwable);

        synchronized (lock) {
            // Print our stack trace
            stream.println(throwable);
            // Actually call => getOurStackTrace().clone(), getOurStackTrace is private method
            StackTraceElement[] trace = throwable.getStackTrace();
            for (StackTraceElement traceElement : trace) {
                // 过滤
                stream.println("\tat " + traceElement);
            }

            // Print suppressed exceptions, if any
            for (Throwable se : throwable.getSuppressed())
                printEnclosedStackTrace(stream, trace, SUPPRESSED_CAPTION, "\t", dejaVu, se);

            // Print cause, if any
            Throwable ourCause = throwable.getCause();
            if (ourCause != null)
                printEnclosedStackTrace(stream, trace, CAUSE_CAPTION, "", dejaVu, ourCause);
        }
    }


    /**
     * Print our stack trace as an enclosed exception for the specified
     * stack trace.
     */
    private static void printEnclosedStackTrace(PrintStream s,
                                                StackTraceElement[] enclosingTrace,
                                                String caption,
                                                String prefix,
                                                Set<Throwable> dejaVu, Throwable throwable) {
        assert Thread.holdsLock(lock);
        if (dejaVu.contains(throwable)) {
            s.println("\t[CIRCULAR REFERENCE:" + throwable + "]");
        } else {
            dejaVu.add(throwable);
            // Compute number of frames in common between this and enclosing trace
            StackTraceElement[] trace = throwable.getStackTrace();
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n])) {
                m--;
                n--;
            }
            int framesInCommon = trace.length - 1 - m;
            // Print our stack trace
            s.println(prefix + caption + throwable);
            for (int i = 0; i <= m; i++)
                s.println(prefix + "\tat " + trace[i]);
            if (framesInCommon != 0)
                s.println(prefix + "\t... " + framesInCommon + " more");

            // Print suppressed exceptions, if any
            for (Throwable se : throwable.getSuppressed())
                printEnclosedStackTrace(s, trace, SUPPRESSED_CAPTION, prefix + "\t", dejaVu, se);

            // Print cause, if any
            Throwable ourCause = throwable.getCause();
            if (ourCause != null)
                printEnclosedStackTrace(s, trace, CAUSE_CAPTION, prefix, dejaVu, throwable);
        }
    }
}
