package io.devpl.codegen.fxui;

import com.google.common.eventbus.EventBus;
import io.devpl.codegen.fxui.app.TestApp;
import javafx.application.Application;

public class TestMain {
    public static void main(String[] args) {
        Application.launch(TestApp.class);

        EventBus bus = new EventBus();
    }
}
