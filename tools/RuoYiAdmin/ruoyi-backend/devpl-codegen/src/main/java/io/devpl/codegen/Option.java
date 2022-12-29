package io.devpl.codegen;

public interface Option {

    /**
     * Set a currentValue to an option
     * @return this
     */
    Option setValue(Object value);

    /*
     * returns True if the option has a currentValue
     */
    boolean hasValue();

    /*
     * returns the currentValue as an Integer
     */
    int asInt();

    /*
     * returns the currentValue as a String
     */
    String asString();

    /**
     * returns the currentValue as a Boolean
     * @return currentValue
     */
    boolean asBoolean();

    /**
     * returns the currentValue as a generic type
     * @param <T> generic type
     * @return currentValue
     */
    <T> T asValue();
}
