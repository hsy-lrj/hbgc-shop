package com.hsy.service;


import com.hsy.domain.Order;
import com.hsy.util.Pager;

import java.util.List;
import java.util.Map;

public interface IOrderService {

	/**
	 * 查询指定商家的商品订单并分页,如果是管理员,就获取所有,如果是商家,就只获取该商家相关订单
	 * *@param userId
	 * @param search
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Map<String, Object>> find(int userId,String search, int page, int size);
	/**
	 * 根据消费用户ID 获取所有订单列表
	 * 
	 * @param consumeId
	 * @return
	 */
	public List<Map<String, Object>> listByConsumeId(int consumeId);
	/**
	 * 添加订单 需要生成下单时间
	 * @param order
	 */
	public void add(Order order) ;
	
	public Order load(int orderId);

	/**
	 * 删除订单,自动删除物流信息
	 * @param orderId
	 */
	public void delete(int orderId);
}
