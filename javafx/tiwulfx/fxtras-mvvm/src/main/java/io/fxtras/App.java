package io.fxtras;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;

public class App {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new JavaFXModule());
        Controller controller = injector.getInstance(Controller.class);

        System.out.println(controller);
    }
}
