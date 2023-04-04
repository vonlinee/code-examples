package io.devpl.toolkit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    /**
     * 复制流
     *
     * @param in  输入流
     * @param out 输出流
     */
    public static void copy(InputStream in, OutputStream out) {
        if (in == null) return;
        try (in) {
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) > 0) {
                out.write(b, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
