module io.devpl.fxui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens io.devpl.fxui to javafx.fxml;
    exports io.devpl.fxui;
    exports io.devpl.fxui.view;
    opens io.devpl.fxui.view to javafx.fxml;
}