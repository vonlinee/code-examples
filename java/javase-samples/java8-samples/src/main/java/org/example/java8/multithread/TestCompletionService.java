package org.example.java8.multithread;

public class TestCompletionService {
    public static void main(String[] args) {
        System.out.println();

        Thread.State state = Thread.currentThread()
                .getState();

        boolean b = state == Thread.State.BLOCKED;
    }
}