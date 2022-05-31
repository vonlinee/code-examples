package io.maker.codegen.core;

import java.io.OutputStream;

/**
 * 代码生成器
 */
public interface CodeGeneration {

    void initialize();

    /**
     * generate code to OutputStream
     * @param output single OutputStream
     */
    void generate(OutputStream output);
}
