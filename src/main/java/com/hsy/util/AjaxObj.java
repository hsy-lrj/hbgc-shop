package com.hsy.util;

/**
 * 专门用来返回ajax处理之后的结果
 */
public class AjaxObj {
	/**
	 * 1 表示成功 , 0 表示 失败
	 */
	private int code;
	/**
	 * 处理完成的提示信息
	 */
	private String msg;

	/**
	 * 附加对象
	 */
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AjaxObj(int code, String msg, Object obj) {
		super();
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}

	public AjaxObj(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public AjaxObj(int code) {
		super();
		this.code = code;
	}

}
