package com.hsy.domain;

/**
 * 订单
 */
public class Order {
	private int orderId;
	/**
	 * 消费者ID(登陆购买商品的用户的userId)
	 */
	private int consumeId;
	/**
	 * 商家ID(购买的商品中对应的userId)
	 */
	private int shopsId;

	/**
	 * 商品ID
	 */
	private int goodsId;
	/**
	 * 购买数量
	 */
	private int amount;
	
	/**
	 * 下单时间
	 */
	private String orderDate;
	
	/**
	 * 收货地址
	 */
	private String addr;

	/**
	 * 总价
	 */
	private double price;

	public int getOrderId() {
		return orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getConsumeId() {
		return consumeId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setConsumeId(int consumeId) {
		this.consumeId = consumeId;
	}

	public int getShopsId() {
		return shopsId;
	}

	public void setShopsId(int shopsId) {
		this.shopsId = shopsId;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
