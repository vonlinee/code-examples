package org.example;

import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 1_000_000; i++) {
      new Thread(() -> {
        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }).start();
    }
    TimeUnit.MINUTES.sleep(3);
  }
}