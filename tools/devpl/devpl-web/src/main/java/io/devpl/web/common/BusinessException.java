package io.devpl.web.common;

import java.util.LinkedList;
import java.util.List;

public class BusinessException extends RuntimeException {

    private boolean ignored;
    private static final long serialVersionUID = 5313036101535701088L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public boolean isIgnored() {
        return ignored;
    }

    @Override
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        StackTraceElement[] stackTraces = super.getStackTrace();
        return removeIgnoredStackTrace(stackTraces);
    }

    private StackTraceElement[] removeIgnoredStackTrace(StackTraceElement[] stackTraces) {
        List<StackTraceElement> toBeShowed = new LinkedList<>();
        for (StackTraceElement stackTrace : stackTraces) {
            if (!stackTrace.getClassName().contains("java")) {
                toBeShowed.add(stackTrace);
            }
        }
        return toBeShowed.toArray(new StackTraceElement[0]);
    }

    // =========================================================================
    // static method
    // =========================================================================

    public static BusinessException create(String message) {
        return new BusinessException(message);
    }
}
