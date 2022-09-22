package io.devpl.logging.internal;

/**
 * Creates messages. Implementations can provide different message format syntaxes.
 */
public interface MessageFactory {

    /**
     * Creates a new message based on an Object.
     * @param message a message object
     * @return a new message
     */
    Message newMessage(Object message);

    /**
     * Creates a new message based on a String.
     * @param message a message String
     * @return a new message
     */
    Message newMessage(String message);

    /**
     * Creates a new parameterized message.
     * @param message a message template, the kind of message template depends on the implementation.
     * @param params  the message parameters
     * @return a new message
     */
    Message newMessage(String message, Object... params);
}