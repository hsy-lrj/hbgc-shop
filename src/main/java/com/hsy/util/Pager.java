package com.hsy.util;

import java.util.List;

public class Pager<E> {
	/**
	 * 状态码  0 成功
	 */
	private int code;
	/**
	 * 信息,一般是错误信息
	 */
	private String msg;
	/**
	 * 总条数
	 */
	private int count;
	/**
	 * 显示的数据,需要转换为json格式传递
	 */
	private List<E> data;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

}
