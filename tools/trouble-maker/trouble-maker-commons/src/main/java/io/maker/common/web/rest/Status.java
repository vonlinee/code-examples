package io.maker.common.web.rest;

import io.devpl.utils.AbstractConstant;
import io.devpl.utils.ConstantPool;

import java.io.Serializable;
import java.util.UUID;
import java.util.function.Function;

public class Status extends AbstractConstant<Status> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3880081106510633049L;

	private static final ConstantPool<Status> pool = new ConstantPool<>() {
		@Override
		protected Status newConstant(int id, String name) {
			return new Status(id, name);
		}
	};

	protected int code;
	protected String message;

	private static Function<Status, Boolean> rule;

	static {
		rule = rd -> rd.code == 200;
	}

	public static void updateRule(Function<Status, Boolean> newRule) {
		rule = newRule;
	}

	public static Status valueOf(int code, String message) {
		// 不存在会创建
		Status rd = (Status) pool.valueOf(UUID.randomUUID().toString());
		rd.code = code;
		rd.message = message;
		return rd;
	}

	public static Status valueOf(String name, int code, String message) {
		// 不存在会创建
		Status rd = (Status) pool.valueOf(name);
		rd.code = code;
		rd.message = message;
		return rd;
	}

	/**
	 * Creates a new instance.
	 *
	 * @param id
	 * @param name
	 */
	protected Status(int id, String name) {
		super(id, name);
	}

	/**
	 * 状态枚举
	 */
	public static final Status HTTP_200 = valueOf("http-200", 200, "正常");
	public static final Status HTTP_404 = valueOf("http-404", 404, "资源不存在");
	public static final Status OK = valueOf(200, "正确");
	public static final Status BAD_REQUEST = valueOf(400, "错误的请求");
	public static final Status UNAUTHORIZED = valueOf(401, "禁止访问");
	public static final Status NOT_FOUND = valueOf(404, "没有可用的数据");
	public static final Status PWD_ERROR = valueOf(300, "密码错误");
	public static final Status EXIT = valueOf(403, "已经存在");
	public static final Status INTERNAL_SERVER_ERROR = valueOf(500, "服务器遇到了一个未曾预料的状况");
	public static final Status SERVICE_UNAVAILABLE = valueOf(503, "服务器当前无法处理请求");
	public static final Status ERROR = valueOf(9999, "数据不能为空");

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public final boolean isSuccessful() {
		return rule.apply(this);
	}
}
