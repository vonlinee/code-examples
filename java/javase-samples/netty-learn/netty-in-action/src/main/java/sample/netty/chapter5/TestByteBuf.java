package sample.netty.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class TestByteBuf {

	public static void main(String[] args) {
		
		
		PooledByteBufAllocator.defaultPageSize();
		
		
		
		ByteBuf heapBuffer = Unpooled.buffer();
		
		// 检查 ByteBuf 是否由数组支撑。如果不是，则这是一个直接缓冲区
		if (heapBuffer.hasArray()) {
			byte[] array = heapBuffer.array();
			int offset = heapBuffer.arrayOffset() + heapBuffer.readerIndex();
			int length = heapBuffer.readableBytes(); // 可读字节数
			
		}
		// 当 hasArray()方法返回 false 时，尝试访问支撑数组将触发一个 Unsupported
		// OperationException。这个模式类似于 JDK 的 ByteBuffer 的用法。
		
		
		
		
		
		
	}
}
