package io.devpl.codegen.core.meta.generator;

import java.io.OutputStream;

public interface CodeGenerator {
    void write(OutputStream output);
}
