package io.maker.common.web.rest;

import java.io.Serializable;

public abstract class Result<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8939479680323102941L;
	
	protected int code;
	protected String msg;
	protected T data;
	
	/**
	 * 时间戳
	 */
    protected long timestamp;
    
    /**
     * 异常调用栈
     */
    protected String exceptionStack;

    /**
     * 序列化
     * @return
     */
    protected abstract String serialize();

    public void setCode(int code) {
    	this.code = code;
    }
    
    public final int getCode() {
    	return code;
    }
    
    public final String getMessage() {
    	return msg;
    }
    
    public void setMessage(String message) {
    	this.msg = message;
    }
}
