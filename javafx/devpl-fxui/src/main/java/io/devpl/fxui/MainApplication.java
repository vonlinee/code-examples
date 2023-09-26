package io.devpl.fxui;

import io.devpl.fxui.editor.EditorNode;
import io.devpl.fxui.editor.LanguageMode;
import io.devpl.fxui.layout.LayoutPane;
import io.devpl.fxui.layout.NavigationMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LayoutPane layoutPane = new LayoutPane();

        Button btn = new Button("AAA");

        NavigationMenu menu = new NavigationMenu("A", btn);

        menu.addChild("A1", new MenuBar());
        menu.addChild("A2", new EditorNode(LanguageMode.JSON));

        layoutPane.addNavigationMenu(menu);

        Scene scene = new Scene(layoutPane, 800, 640);
        stage.setTitle("Devpl");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}