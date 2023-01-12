package io.devpl.toolkit.framework;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 统一管理字符串提示信息
 */
@SuppressWarnings("unused")
public final class MessageText {

    private static final String BUNDLE_NAME = "org.mybatis.generator.internal.util.messages.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private MessageText() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String param1) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), param1);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String param1, String param2) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), param1, param2);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String param1, String param2,
                                   String param3) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), param1, param2, param3);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
