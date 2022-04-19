package io.netty.nio.nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

/**
 * pipe实现了单向通信的一对Channel
 * 
 * Pipe包含了
 * 1.Pipe.SinkChannel
 * 2.Pipe.SourceChannel
 */
public class TestPipe {

	public static void main(String[] args) throws IOException {
		// 打开管道 
		Pipe pipe = Pipe.open();
		Pipe pipe2 = Pipe.open();
		System.out.println(pipe == pipe2); // false，非单例
		
		// 要向管道写数据，需要访问sink通道
		Pipe.SinkChannel sinkChannel = pipe.sink();
		String newData = "New String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf_write = ByteBuffer.allocate(48);
		buf_write.clear();
		buf_write.put(newData.getBytes(StandardCharsets.UTF_8));
		buf_write.flip(); // 切换buf的读写状态

		while (buf_write.hasRemaining()) {
			sinkChannel.write(buf_write);
		}

		// 读取数据
		Pipe.SourceChannel sourceChannel = pipe.source();
		ByteBuffer buf_read = ByteBuffer.allocate(48);
		// read()方法返回的int值会告诉我们多少字节被读进了缓冲区
		int bytesRead = sourceChannel.read(buf_read);
		System.out.println("Read bytes count = " + bytesRead);
		System.out.println("Read Data : " + new String(buf_read.array(), StandardCharsets.UTF_8));
	}
}
