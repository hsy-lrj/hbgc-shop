package com.hsy.service.impl;

import com.hsy.dao.IGoodsDao;
import com.hsy.dao.IShoppingCartDao;
import com.hsy.domain.Goods;
import com.hsy.domain.Order;
import com.hsy.domain.ShoppingCart;
import com.hsy.service.IOrderService;
import com.hsy.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 购物车管理
 */
@Service("ShoppingCartService")
public class ShoppingCartService implements IShoppingCartService {

	@Autowired
	private IShoppingCartDao shoppingCartDao;
	@Autowired
	private IGoodsDao goodsDao;
	@Autowired
	private IOrderService orderService;
	@Override
	public List<Map<String, Object>> list(int userId) {
		return shoppingCartDao.list(userId);
	}

	@Override
	public void add(ShoppingCart shoppingCart) {
		// 判断该用户的这个商品是否已经有对应的购物车信息
		ShoppingCart oldShoppingCart = shoppingCartDao.load(
				shoppingCart.getUserId(), shoppingCart.getGoodsId());
		if (oldShoppingCart != null) {
			// 设置新的物品数量,等于已有 + 新增
			oldShoppingCart.setAmount(oldShoppingCart.getAmount()
					+ shoppingCart.getAmount());
			shoppingCartDao.update(oldShoppingCart);
		} else {
			shoppingCartDao.add(shoppingCart);
		}

	}

	@Override
	public void delete(int shoppingCartId) {
		shoppingCartDao.delete(shoppingCartId);

	}

	@Override
	public void update(ShoppingCart shoppingCart) {
		shoppingCartDao.update(shoppingCart);

	}

	@Override
	public void accounts(int[] shoppingCartIds) {
		for (int shoppingCartId : shoppingCartIds) {
			// 查询购物车详细信息
			ShoppingCart shoppingCart = shoppingCartDao.load(shoppingCartId);
			// 根据购物车对象中的商品ID 查询商品详细信息
			Goods goods = goodsDao.load(shoppingCart.getGoodsId());
			// 创建订单
			Order order = new Order();
			order.setAmount(shoppingCart.getAmount());
			order.setConsumeId(shoppingCart.getUserId());
			order.setGoodsId(shoppingCart.getGoodsId());
			order.setShopsId(goods.getUserId());
			order.setPrice(shoppingCart.getAmount() * goods.getPrice());
			
			// 添加订单
			orderService.add(order);
			// 删除购物车
			delete(shoppingCartId);
		}
	}
}
