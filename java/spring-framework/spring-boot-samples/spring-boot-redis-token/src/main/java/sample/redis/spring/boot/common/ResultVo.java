package sample.redis.spring.boot.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回类
 * 
 * @Author: jinv
 * @CreateTime: 2020-05-15 18:08
 * @Description:
 */
@Data
public class ResultVo<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	// 响应码
	private int code;
	// 返回消息
	private String msg;
	// 数据
	private T data;

	// 成功
	public ResultVo() {
		this.code = 0;
		this.msg = "成功";
	}

	// 成功返回数据
	public ResultVo(T data) {
		this();
		this.data = data;
	}

	// 成功，返回消息
	public ResultVo(String msg) {
		this();
		this.msg = msg;
	}

	// 失败，返回错误码和失败消息
	public ResultVo(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	// 失败，返回固定500错误码和异常消息
	public ResultVo(Throwable e) {
		this.code = 500;
		this.msg = e.getMessage();
	}

	// 失败，返回自定义的错误码和异常消息（使用场景，ExceptionHandler隐藏异常细节，直接返回500和未知异常，请联系管理员）
	public ResultVo(int code, Throwable e) {
		this.code = code;
		this.msg = e.getMessage();
	}

}
