package com.hsy.domain;

/**
 * 购物车
 */
public class ShoppingCart {
	private int shoppingCartId;
	/**
	 * 商品ID
	 */
	private int goodsId;
	/**
	 * 用户ID
	 */
	private int userId;
	/**
	 * 购买的数量
	 */
	private int amount;



	public int getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(int shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
