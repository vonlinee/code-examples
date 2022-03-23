package sample.java.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFileChannel {
	public static void main(String[] args) throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("D:\\1.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			System.out.println("Read " + bytesRead);
			buf.flip(); // 反转缓冲区
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			buf.clear(); // 清空缓冲区
			bytesRead = inChannel.read(buf); // 处理完后继续从channel中读取数据到Buffer
		}
		aFile.close();// RandomAccessFile的close方法会将对应的非空channel关闭
	}

	public static void test2() throws IOException {
		String file = "D:/1.jpg";
		FileChannel inChannel = FileChannel.open(Paths.get(file), StandardOpenOption.READ);
		OpenOption[] options = new OpenOption[] { 
				StandardOpenOption.READ,
				StandardOpenOption.WRITE,
				StandardOpenOption.CREATE_NEW //文件存在则报错，不存在则创建
		};
		FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), options);
		long inSize = inChannel.size();
		// 内存映射文件,缓冲区直接在内存，程序与物理内存直接交换数据
		MappedByteBuffer inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inSize);
		MappedByteBuffer outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inSize);
		// 操作缓冲区
		byte[] bytes = new byte[1024];
		while (inBuffer.hasRemaining()) {
			inBuffer.get(bytes);
			outBuffer.put(bytes);//直接放到缓冲区
		}
		// 关闭通道
		inChannel.close();
		outChannel.close();
	}
}
