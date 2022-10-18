package io.devpl.codegen.fxui;

import io.devpl.codegen.fxui.app.SnippetApplication;
import io.devpl.codegen.fxui.frame.JavaFXApplication;

public class MainLauncher {

    public static void main(String[] args) {
        JavaFXApplication.run(SnippetApplication.class, args);
    }
}