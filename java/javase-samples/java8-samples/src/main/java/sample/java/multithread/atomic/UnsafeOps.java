package sample.java.multithread.atomic;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeOps {

	@SuppressWarnings("restriction")
	private static final Unsafe unsafe;
	
	static {
		unsafe = getUnsafe();
	}
	
	@SuppressWarnings("restriction")
	private static Unsafe getUnsafe() {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
