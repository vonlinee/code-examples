package sample.java8.primary.oop.inherit;

import java.lang.reflect.Method;

public class TestInheriteFeature {
	public static void main(String[] args) {
		IA impl1 = new Impl();
		IB impl2 = new Impl();
		
		impl1.add("AAA");
		impl2.add("BBB");
		
		
		
		
	}
}

class Impl implements IA, IB {
	@Override
	public void add(String str) {
		try {
			Method method = super.getClass().getMethod("add", new Class<?>[] {String.class});
			System.out.println(str + " -> " + method);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}

interface IA {
	void add(String str);
}

interface IB {
	void add(String str);
}

interface IC {
	void addString(String str);
}