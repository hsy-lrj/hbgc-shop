package com.hsy.dao;

import com.hsy.domain.Order;
import com.hsy.util.Pager;

import java.util.List;
import java.util.Map;

public interface IOrderDao {
	/**
	 * 根据商品查询所有订单
	 * 
	 * @param userId
	 * @return
	 */
	public List<Order> listByGoodsId(int goodsId);

	/**
	 * 根据消费用户ID 获取所有订单列表
	 * 
	 * @param consumeId
	 * @return
	 */
	public List<Map<String, Object>> listByConsumeId(int consumeId);

	/**
	 * 查询指定商家的商品订单并分页,如果userId是0,就查询所有订单并分页
	 * 
	 * @param sreach
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Map<String, Object>> find(int userId, String search, int page, int size);

	public void add(Order order);

	public Order load(int orderId);

	public void delete(int orderId);

	/**
	 * 订单修改,应该可以修改收货地址,我们是写死的,所以我们规定订单不可以更改
	 * 
	 * @param user
	 */
	public void update(Order order);
}
