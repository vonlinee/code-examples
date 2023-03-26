package io.devpl.tookit.utils;

import io.devpl.sdk.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 从配置文件中加载所有的提示文本
 */
public final class Messages {

    private Messages() {
    }

    private static final Properties messages = new Properties();

    static {
        try (FileInputStream inputStream = new FileInputStream(ResourceUtils.getResourcesAsFile("message.properties", true));) {
            messages.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(String id) {
        final Object text = messages.get(id);
        if (text == null) {

        }
        return String.valueOf(text);
    }
}