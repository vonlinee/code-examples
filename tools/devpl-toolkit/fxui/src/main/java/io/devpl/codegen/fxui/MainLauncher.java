package io.devpl.codegen.fxui;

import io.devpl.codegen.fxui.app.LauncherApplication;
import javafx.application.Application;

public class MainLauncher {
    public static void main(String[] args) {
        LauncherApplication.args = args;
        Application.launch(LauncherApplication.class, args);
    }
}