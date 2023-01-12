package use;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.BasicMarker;

public class Test {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Test.class);


        System.out.println(logger.getClass());

        final Marker marker = MarkerFactory.getMarker("AAA");

        BasicMarker marker1;

        System.out.println(marker.getClass());
        logger.info(marker, "日志信息");
    }
}
