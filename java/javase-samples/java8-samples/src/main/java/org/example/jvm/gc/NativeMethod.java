package org.example.jvm.gc;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author vonline
 * @since 2022-07-25 1:54
 */
public class NativeMethod {

    public static native void nativeMethod();

    private static NioSocketChannel channel;

    public static void main(String[] args) {

    }
}
