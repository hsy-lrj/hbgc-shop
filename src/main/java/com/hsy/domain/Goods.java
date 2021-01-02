package com.hsy.domain;

/**
 * 商品
 */
public class Goods {
	private int goodsId;
	/**
	 * 商家ID : 哪个商家出售的这个商品
	 */
	private int userId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品单价
	 */
	private double price;

	/**
	 * 商品图片
	 */
	private String imgUrl;

	/**
	 * 商品描述
	 */
	private String desc;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
