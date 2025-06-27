package org.example.java8.multithread;

import javafx.application.Application;
import javafx.stage.Stage;

public class CountDownLatchDemo extends Application {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                Thread.currentThread().interrupt();
                if (Thread.interrupted()) {
                    System.out.println("current thread is interrupted, prepare to exit");
                    break;
                }
            }
        });
        t.start();
        Thread.sleep(3000);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
