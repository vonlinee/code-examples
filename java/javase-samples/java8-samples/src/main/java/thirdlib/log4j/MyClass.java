package thirdlib.log4j;

import java.util.logging.Logger;

public class MyClass {

    private static final Logger LOG = Logger.getLogger(MyClass.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        LOG.info("log");
    }
}
