package org.apache.ddlutils.util;

import java.io.IOException;
import java.io.Reader;

public class IOUtils {

    public static String readString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[4096];
        int len;
        while ((len = reader.read(buf)) >= 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
