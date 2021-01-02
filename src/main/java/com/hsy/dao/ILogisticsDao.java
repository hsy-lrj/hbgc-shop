package com.hsy.dao;

import com.hsy.domain.Logistics;
import com.hsy.util.Pager;

public interface ILogisticsDao {

	/**
	 * 根据订单ID删除物流信息
	 * 
	 * @param orderId
	 */
	public void deleteByOrderId(int orderId);

	/**
	 * 查询指定商家的商品物流信息,如果商家是0,说明是管理员,就获取所有的物流
	 * 
	 * @param sreach
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Logistics> find(int userId, String search, int page, int size);

	public void add(Logistics logistics);

	/**
	 * 根据订单ID查询物流
	 * 
	 * @param orderId
	 * @return
	 */
	public Logistics loadByOrderId(int orderId);
	

	public void delete(int logisticsId);
	public Logistics load(int logisticsId);
	public void update(Logistics logistics);
}
