package codegen.generator;

import java.io.OutputStream;

public interface CodeGenerator {
    void write(OutputStream output);
}
