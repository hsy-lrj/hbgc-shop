package com.hsy.service;

import com.hsy.domain.Logistics;
import com.hsy.util.Pager;
import com.hsy.util.ShopException;

public interface ILogisticsService {
	/**
	 * 短信提醒物流状态
	 * 
	 * @param logisticsId
	 * @throws ShopException
	 */
	public void sendSMS(int logisticsId) throws ShopException;

	/**
	 * 查询指定商家的商品订单并分页,如果userId是0,就查询所有订单并分页
	 * 
	 * @param search
	 * @param page
	 * @param size
	 * @return
	 */
	public Pager<Logistics> find(int userId, String search, int page, int size);

	/**
	 * 添加物流信息,需要判断,如果已有该订单的物流信息,就不添加
	 * 
	 * @param orderId
	 */
	public void add(int orderId) throws ShopException;

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
