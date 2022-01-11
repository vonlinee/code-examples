package org.example.springboot.common;

import java.io.Serializable;

abstract class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 2819999455116368072L;
	private static native void registerNatives();
	
	String timestamp;
	String errorMsg; //错误信息
	ResultDescription description;
	T data;
}
