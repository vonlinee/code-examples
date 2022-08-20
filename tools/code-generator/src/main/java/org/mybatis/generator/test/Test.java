package org.mybatis.generator.test;

import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    private static final Log log = LogFactory.getLog(Test.class);

    public static void main(String[] args) {
        log.info("111111111111");
        logger.info("1222222222");
    }
}
