package io.maker.base.lang;

public interface Holder<T> {
	/**
	 * intialization
	 * @param something
	 */
	void hold(T something);
	
	/**
	 * do something
	 */
	void action();
	
	/**
	 * get
	 * @return
	 */
	T get();
}