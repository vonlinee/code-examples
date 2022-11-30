package io.devpl.sdk.beans.test;

import io.devpl.sdk.beans.Bean;

/**
 * Error class used when two beans fail to compare.
 */
class BeanComparisonError extends AssertionError {

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

    /**
     * The expected bean.
     */
    private final Bean expected;

    /**
     * The actual bean.
     */
    private final Bean actual;

    /**
     * Creates a new error.
     * 
     * @param message  the message, may be null
     * @param expected  the expected value, not null
     * @param actual  the actual value, not null
     */
    BeanComparisonError(String message, Bean expected, Bean actual) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }

    //-------------------------------------------------------------------------
    /**
     * Gets the expected field.
     * @return the expected
     */
    public Bean getExpected() {
        return expected;
    }

    /**
     * Gets the actual field.
     * @return the actual
     */
    public Bean getActual() {
        return actual;
    }
}
