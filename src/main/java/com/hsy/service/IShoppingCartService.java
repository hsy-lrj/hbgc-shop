package com.hsy.service;

import com.hsy.domain.ShoppingCart;
import com.hsy.util.ShopException;

import java.util.List;
import java.util.Map;

public interface IShoppingCartService {

	/**
	 * 根据登陆的用户ID获取该用户的购物车信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> list(int userId);

	/**
	 * 商品添加,如果该用户的购物车中已有该商品,那么数量+1
	 * 
	 * @param shoppingCart
	 * @throws ShopException
	 */
	public void add(ShoppingCart shoppingCart);

	public void delete(int shoppingCartId);

	/**
	 * 购物车更改只更改数量
	 * 
	 * @param shoppingCart
	 */
	public void update(ShoppingCart shoppingCart);
	/**
	 * 结算,需要删除购物车中信息,并添加成订单
	 * @param shoppingCartIds
	 */
	public void accounts (int[] shoppingCartIds);
}
