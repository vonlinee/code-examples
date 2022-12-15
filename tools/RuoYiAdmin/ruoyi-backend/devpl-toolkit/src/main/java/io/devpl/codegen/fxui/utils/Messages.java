package io.devpl.codegen.fxui.utils;

import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 从配置文件中加载所有的提示文本
 */
public final class Messages {

    private Messages() {
    }

    private static final Properties messages = new Properties();

    private static final Log log = LogFactory.getLog(Messages.class);

    public static void init() {
        try {
            final File file = ResourceUtils.getResourcesAsFile("message.properties", true);
            init(file);
        } catch (FileNotFoundException e) {
            log.error("messages init failed", e);
            System.exit(0);
        }
    }

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
