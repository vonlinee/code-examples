package sample.jvm.gc;

import design.pattern.iterator.NameRepository;
import io.netty.channel.nio.AbstractNioChannel;
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
