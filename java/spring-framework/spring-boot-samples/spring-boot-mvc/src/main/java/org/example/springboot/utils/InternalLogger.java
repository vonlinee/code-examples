package org.example.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternalLogger {

    private static final Logger logger = LoggerFactory.getLogger("组件配置");

    public static void debug(String format, Object... arguments) {
        logger.info("[" + logger.getName() + "]" + format, arguments);
    }
}
