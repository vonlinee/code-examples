package io.devpl.codegen.mbg.fx.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 统一管理字符串提示信息
 */
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

    public static String getString(String key, String parm1) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), parm1);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), parm1, parm2);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2,
                                   String parm3) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), parm1, parm2, parm3);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
