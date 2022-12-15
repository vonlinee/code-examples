package io.devpl.logging.format;

/**
 * Holds the results of formatting done by {@link MessageFormatter}.
 * @author Joern Huxhorn
 */
public class FormattedMessage {

    public static final FormattedMessage NULL = new FormattedMessage(null);

    private final String message;
    private final Throwable throwable;
    private final Object[] argArray;

    public FormattedMessage(String message) {
        this(message, null, null);
    }

    public FormattedMessage(String message, Object[] argArray, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
        this.argArray = argArray;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getArgArray() {
        return argArray;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
