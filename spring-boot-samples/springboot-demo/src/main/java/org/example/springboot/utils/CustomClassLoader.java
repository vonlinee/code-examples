package org.example.springboot.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		File classFile = new File(name);
		if (!classFile.exists()) {
			throw new RuntimeException(name + " doesnot exists!");
		}
		ByteArrayOutputStream baos = null;
		try (InputStream classData = new FileInputStream(classFile)) {
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			while(-1 != classData.read(buffer)) {
				baos.write(buffer);
				baos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		if (bytes == null || bytes.length == 0) {
			throw new RuntimeException("load class data failed");
		}
		return defineClass(name, bytes, 0, bytes.length, null);
	}
}
