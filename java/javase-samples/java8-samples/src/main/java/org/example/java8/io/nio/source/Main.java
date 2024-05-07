package org.example.java8.io.nio.source;

import org.junit.jupiter.api.Test;

import java.nio.channels.spi.SelectorProvider;

public class Main {


    @Test
    public void test1() {
        SelectorProvider provider = SelectorProvider.provider();

        SelectorProvider provider1 = SelectorProvider.provider();

        System.out.println(provider == provider1);
    }
}



