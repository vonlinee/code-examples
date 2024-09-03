package org.example.java8.utils;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    public void test1() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(111);
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 3);
    }
}
