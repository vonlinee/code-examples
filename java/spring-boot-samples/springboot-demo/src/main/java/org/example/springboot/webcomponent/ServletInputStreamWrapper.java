package org.example.springboot.webcomponent;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class ServletInputStreamWrapper extends ServletInputStream {

	private ByteArrayInputStream byteArrayInputStream;
	
	public ServletInputStreamWrapper(ByteArrayInputStream inputStream) {
		this.byteArrayInputStream = inputStream;
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(ReadListener listener) {
		
	}

	@Override
	public int read() throws IOException {
		return byteArrayInputStream.read();
	}
}
