package code.pocket.base.rest;

import java.io.Serializable;

abstract class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 2819999455116368072L;
	
	String timestamp;
	String errorMsg;
	ResultDescription description;
	T data;
}
