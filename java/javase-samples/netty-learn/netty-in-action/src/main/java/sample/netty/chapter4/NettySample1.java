package sample.netty.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;

public class NettySample1 {
	public static void main(String[] args) {
		
		ByteBuf buf = Unpooled.copiedBuffer("AAVDC".getBytes());
		ReferenceCountUtil.release(buf);
	}
}
