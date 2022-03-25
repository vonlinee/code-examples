package io.maker.base;

import io.maker.base.lang.Value;

public final class Matrix {

    private int x;
    private int y;

    private final Value[][] data;

    public Matrix(int i, int j) {
        this.x = i;
        this.y = j;
        this.data = new Value[i][j];
    }
}
