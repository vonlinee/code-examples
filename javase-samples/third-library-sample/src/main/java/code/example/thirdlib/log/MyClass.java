package code.example.thirdlib.log;

import java.util.logging.Logger;

public class MyClass {

    private static final Logger LOG = Logger.getLogger(MyClass.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "jdklog.properties");
        LOG.info("log");
    }
}
