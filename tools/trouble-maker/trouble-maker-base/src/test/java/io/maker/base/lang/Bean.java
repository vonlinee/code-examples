package io.maker.base.lang;

import io.maker.base.lang.reflect.TypeMetadata;

public class Bean extends TypeMetadata {

	protected Bean(Object target) {
		super(target);
	}

	public static void main(String[] args) {
		
		Bean bean = new Bean(new Object());
		
		System.out.println(bean.isString());
	}
}
