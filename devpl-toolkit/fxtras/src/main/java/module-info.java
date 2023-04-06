open module fxtras {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.slf4j;
    requires javafx.controls;
    requires cglib;
    requires org.jetbrains.annotations;

    requires devpl.logging;

    requires devpl.eventbus;
    requires java.logging;


    exports io.devpl.fxtras.mvc;
    exports io.devpl.fxtras;
    exports io.devpl.fxtras.utils;
    exports io.devpl.fxtras.beans;
}