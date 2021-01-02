package com.hsy.domain;

/**
 * 物流
 */
public class Logistics {
	private int logisticsId;
	/**
	 * 订单ID  唯一,一个订单ID只有一条物流信息
	 */
	private int orderId;
	/**
	 * 物流状态 : 已出库、已揽件、已发货、派送中、已收货
	 */
	private String status;

	public int getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(int logisticsId) {
		this.logisticsId = logisticsId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
