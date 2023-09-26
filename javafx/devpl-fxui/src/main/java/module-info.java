module io.devpl.fxui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.jsobject;
    requires javafx.web;

    requires org.fxmisc.richtext;

    opens io.devpl.fxui to javafx.fxml;
    exports io.devpl.fxui;
    exports io.devpl.fxui.view;
    opens io.devpl.fxui.view to javafx.fxml;
    exports io.devpl.fxui.layout;
    opens io.devpl.fxui.layout to javafx.fxml;
}