open module fxtras {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;

    requires javafx.controls;
    requires cglib;

    requires devpl.eventbus;
    requires java.logging;
    requires org.slf4j;


    exports io.devpl.fxtras.mvc;
    exports io.devpl.fxtras;
    exports io.devpl.fxtras.utils;
    exports io.devpl.fxtras.beans;
}