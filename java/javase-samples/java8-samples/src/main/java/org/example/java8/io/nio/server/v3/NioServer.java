package org.example.java8.io.nio.server.v3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    private InetSocketAddress IP = new InetSocketAddress("127.0.0.1", 8888);

    public NioServer() {

    }

    private void init() {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(IP);
            Selector selector = Selector.open();
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_ACCEPT | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT);
            while (true) {
                int readyChannels = 0;
                try {
                    readyChannels = selector.select();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (readyChannels == 0)
                    continue;
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isAcceptable()) {
                        // a connection was accepted by a ServerSocketChannel.
                        SocketChannel sc = ssc.accept();
                    } else if (key.isConnectable()) {
                        // a connection was established with a remote server.
                    } else if (key.isReadable()) {
                        // a channel is ready for reading
                    } else if (key.isWritable()) {
                        // a channel is ready for writing
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void response() throws IOException {
        int port = 9999; // the port to listen
        ServerSocketChannel channel = ServerSocketChannel.open(); // here we create a ServerSocketChannel
        channel.configureBlocking(false); // set channel to non-blocking mode, becareful, FileChannel can not be set to non-blocking mode!
        channel.socket().bind(new InetSocketAddress(port)); // bind address on port
        Selector selector = Selector.open(); // create a selector
        SelectionKey selKey = channel.register(selector, SelectionKey.OP_ACCEPT); // regist selector upon channel for interest `accept` event
        int interestSet = selKey.interestOps(); // get interest set
        boolean is_accept = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
        while (true) {
            int readyChannels = 0;
            try {
                readyChannels = selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readyChannels == 0)
                continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    // 不应该通过SocketChannel sc = channel.accept();获取客户端Channel

                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
        server.response();
    }
}
