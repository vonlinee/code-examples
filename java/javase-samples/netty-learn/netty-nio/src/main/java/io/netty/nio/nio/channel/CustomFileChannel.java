package io.netty.nio.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CustomFileChannel extends AbstractInterruptibleChannel
		implements SeekableByteChannel, GatheringByteChannel, ScatteringByteChannel {

	public CustomFileChannel() {
		try {
			FileChannel fc = FileChannel.open(Paths.get(""), StandardOpenOption.READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {

		return 0;
	}

	@Override
	public long read(ByteBuffer[] dsts) throws IOException {

		return 0;
	}

	@Override
	public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
		return 0;
	}

	@Override
	public long write(ByteBuffer[] srcs) throws IOException {
		return 0;
	}

	@Override
	public int read(ByteBuffer dst) throws IOException {
		return 0;
	}

	@Override
	public int write(ByteBuffer src) throws IOException {
		return 0;
	}

	@Override
	public long position() throws IOException {
		return 0;
	}

	@Override
	public SeekableByteChannel position(long newPosition) throws IOException {
		return null;
	}

	@Override
	public long size() throws IOException {
		return 0;
	}

	@Override
	public SeekableByteChannel truncate(long size) throws IOException {
		return null;
	}

	@Override
	protected void implCloseChannel() throws IOException {

	}
}
