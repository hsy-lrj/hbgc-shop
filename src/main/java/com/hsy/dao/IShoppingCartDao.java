package com.hsy.dao;

import com.hsy.domain.ShoppingCart;
import com.hsy.util.ShopException;

import java.util.List;
import java.util.Map;

public interface IShoppingCartDao {
	
	/**
	 * 根据登陆的用户ID获取该用户的购物车信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> list(int userId);
	/**
	 * 商品添加
	 * @param user
	 * @throws ShopException
	 */
	public void add(ShoppingCart shoppingCart) ;
	
	/**
	 * 根据用户ID和商品ID查看某一个购物车
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public ShoppingCart load(int userId,int goodsId);
	/**
	 * 根据购物车ID 查询
	 * @param shoppingCartId
	 * @return
	 */
	public ShoppingCart load(int shoppingCartId);

	public void delete(int shoppingCartId);
	/**
	 * 购物车更改只更改数量
	 * @param user
	 */
	public void update(ShoppingCart shoppingCart);

}
