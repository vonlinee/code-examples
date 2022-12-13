package io.devpl.codegen.fxui.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.NavigationActions;

public class TestCodeAreaApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final CodeArea codeArea = new CodeArea();
        codeArea.setPadding(new Insets(10, 8, 10, 8));
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setAutoHeight(true);
        codeArea.setAutoScrollOnDragDesired(true);
        codeArea.start(NavigationActions.SelectionPolicy.ADJUST);
        final Scene scene = new Scene(codeArea, 400.0, 500.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
