package io.devpl.eventbus;

import java.util.logging.Level;

public interface Logger {

    void log(Level level, String msg);

    void log(Level level, String msg, Throwable th);

    class JavaLogger implements Logger {
        protected final java.util.logging.Logger logger;

        public JavaLogger(String tag) {
            logger = java.util.logging.Logger.getLogger(tag);
        }

        @Override
        public void log(Level level, String msg) {
            // TODO Replace logged method with caller method
            logger.log(level, msg);
        }

        @Override
        public void log(Level level, String msg, Throwable th) {
            // TODO Replace logged method with caller method
            logger.log(level, msg, th);
        }

    }

    class SystemOutLogger implements Logger {

        @Override
        public void log(Level level, String msg) {
            System.out.println("[" + level + "] " + msg);
        }

        @Override
        public void log(Level level, String msg, Throwable th) {
            System.out.println("[" + level + "] " + msg);
            th.printStackTrace(System.out);
        }

    }

    class Default {
        public static Logger get() {
            return new SystemOutLogger();
        }
    }

}
