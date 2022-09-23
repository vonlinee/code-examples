package io.devpl.sdk.internal.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.dsig.XMLSignature;
import java.util.List;

public class TestEnumPool {

    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        Logger logger = LoggerFactory.getLogger("enum");
        logger.info("log message {}", XMLSignature.class);
    }

    public static void test1() {
        List<StatusCode> statuses = StatusCode.listAll();
        System.out.println(statuses);
        StatusCode.update(300, "重定向", true);
        statuses = StatusCode.listAll();
        System.out.println(statuses);
    }
}
