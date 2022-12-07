package io.devpl.codegen.fxui.utils;

import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Messages {

    private static final Properties messages = new Properties();

    private static Log log = LogFactory.getLog(Messages.class);

    public static void init(File file) {
        try (FileInputStream inputStream = new FileInputStream(file);) {
            messages.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(String id) {
        final Object text = messages.get(id);
        if (text == null) {
            log.warn("消息ID不存在: {}", id);
        }
        return String.valueOf(text);
    }
}
