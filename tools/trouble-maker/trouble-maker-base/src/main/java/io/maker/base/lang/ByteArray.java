package io.maker.base.lang;

import java.nio.charset.Charset;

public class ByteArray {

    private byte[] bytes;
    private Charset encoding;

    private ByteArray(byte[] bytes, Charset encoding) {
        this.bytes = bytes;
        this.encoding = encoding;
    }

    private ByteArray(byte[] bytes) {
        this.bytes = bytes;
        this.encoding = Charset.defaultCharset();
    }

    public static ByteArray wrap(byte[] bytes) {
        return new ByteArray(bytes);
    }

    public static ByteArray wrap(byte[] bytes, Charset encoding) {
        return new ByteArray(bytes, encoding);
    }

    public static ByteArray allocate(int len) {
        return new ByteArray(new byte[len], null);
    }

    public byte[] unwrap() {
        return bytes;
    }

    public byte[] unwrap(Charset encoding) {
        if (this.encoding == null || this.encoding.equals(encoding)) {
            return bytes;
        }
        return new String(bytes, this.encoding).getBytes(encoding);
    }
}
