package io.devpl.logging;

//import org.apache.log4j.pattern.PatternParser;
//import org.slf4j.impl.Reload4jLoggerAdapter;

// import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {

    public static void main(String[] args) {

        Logger logger = LogManager.getLogger(Test.class);

        org.apache.logging.log4j.core.Logger log = (org.apache.logging.log4j.core.Logger) logger;

        log.error("测试 {}", "zs");

//        Reload4jLoggerAdapter adapter;
//        // Log4j12LoggerAdapter adapter1;
//        logger.info("name = {}, age = {}", "zs", 30);
//
//        java.util.logging.Logger log = java.util.logging.Logger.getLogger("zs");
//
//        log.info("11111");

//        java.util.logging.Logger jdklogger = java.util.logging.Logger.getLogger("zs");
//
//
//        jdklogger.log(Level.INFO, "11111000");

//        Object[] arguments = {"zs", 23};
//
//        FormattedMessage formattingTuple = MessageFormatter.arrayFormat("name = { {} }", arguments);
//
//        System.out.println(formattingTuple.getMessage());
    }
}
