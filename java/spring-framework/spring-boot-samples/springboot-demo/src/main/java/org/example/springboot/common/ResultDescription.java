package org.example.springboot.common;

import java.util.function.Predicate;

/**
 * 自定义结果的枚举继承此类，只存储状态码和信息，不被序列化
 * @author line
 */
public abstract class ResultDescription implements Cloneable {

	protected String code;
	protected String message;
	protected Predicate<String> rule;//判断结果是否成功
	protected Boolean isSuccessful;
	
	private static final ResultDescription SINGETON = new ResultDescription("", "", null) {}; 
	
	//默认根据状态码200判断
	private static Predicate<String> DEFAULT_SUCCESSFUL_RULE = (code) -> "200".equals(code);
	
	public ResultDescription(String code, String message, Predicate<String> rule) {
		super();
		this.code = code;
		this.message = message;
		this.rule = (rule == null) ? DEFAULT_SUCCESSFUL_RULE : rule;
		this.isSuccessful = DEFAULT_SUCCESSFUL_RULE.test(code);
	}

	public static ResultDescription copy(String code, String message) {
		SINGETON.code = code;
		SINGETON.message = message;
		return SINGETON;
	}

	public final String getCode() {
		return code;
	}

	public final String getMessage() {
		return message;
	}
	
	final boolean isSuccessful() {
		return isSuccessful;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
