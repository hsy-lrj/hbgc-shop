package com.hsy.service.impl;

import com.hsy.dao.ILogisticsDao;
import com.hsy.dao.IOrderDao;
import com.hsy.dao.IUserDao;
import com.hsy.domain.Order;
import com.hsy.domain.User;
import com.hsy.service.IOrderService;
import com.hsy.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单管理
 */
@Service("orderService")
public class OrderService implements IOrderService {

	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ILogisticsDao logisticsDao;
	// -----------------------------

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Pager<Map<String, Object>> find(int userId, String search, int page, int size) {
		search = "%" + search + "%";
		// 判断当前是管理员还是商家
		User user = userDao.load(userId);
		if (user.getRoleName().equals("管理员")) {
			return orderDao.find(0, search, page, size);
		} else if (user.getRoleName().equals("商家")) {
			return orderDao.find(user.getUserId(), search, page, size);
		}
		// 到这里说明不是商家也不是管理员,那就是出错了,因为只有管理员和商家可以进入管理系统
		Pager pager = new Pager();
		pager.setCode(500);
		pager.setMsg("登陆用户的角色有问题,请与管理员联系");
		return pager;
	}

	@Override
	public Order load(int orderId) {
		return orderDao.load(orderId);
	}

	@Override
	public void add(Order order) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		String orderDate = sdf.format(date);
		order.setOrderDate(orderDate);
		orderDao.add(order);
	}

	@Override
	public void delete(int orderId) {
		// 删除物流对应的信息
		logisticsDao.deleteByOrderId(orderId);
		orderDao.delete(orderId);
	}

	@Override
	public List<Map<String, Object>> listByConsumeId(int consumeId) {
		return orderDao.listByConsumeId(consumeId);
	}
}
