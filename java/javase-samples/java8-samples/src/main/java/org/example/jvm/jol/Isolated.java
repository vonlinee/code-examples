package org.example.jvm.jol;

import sun.misc.Contended;

public class Isolated {

    @Contended
    private int v1;

    @Contended
    private long v2;
}